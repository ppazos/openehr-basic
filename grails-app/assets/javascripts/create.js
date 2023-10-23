$(function () {

  // var controls = $(':input');
  // for (i=0; i<controls.length; i++)
  // {
  //   console.log(controls[i].name);

  //   // show data
  //   for (path in data)
  //   {
  //     if (controls[i].name == path)
  //     {
  //       $(controls[i]).val( data[path] );
  //     }
  //   }

  //   // show errors
  //   for (path in errors)
  //   {
  //     console.log(path);

  //     // field has error?
  //     if (controls[i].name.startsWith( path ))
  //     {
  //       $(controls[i]).parent().addClass('has-error');
  //     }
  //   }
  // }


  $('form').on('submit', function (e) {

    console.log('submit', this, this.action);

    e.preventDefault();

    let template_id;
    let values = [];

    $(':input').each(function (i) {

        if (this.tagName == 'BUTTON') return;

        // input with name is the template_id, other inputs will have data-* attributes with paths and archetype_id
        if (this.name && this.name == 'template_id') {
            template_id = this.value;
        }
        else if (Object.keys(this.dataset).length) {
            values.push(
                Object.assign({ value: this.value }, this.dataset)
            );
        }

        console.log(this.dataset.tpath);

        //console.log(i, this.tagName, this.name, this, this.dataset, Object.keys(this.dataset).length);
    });

    console.log(values);

    $.ajax({
      url: this.action,
      type: "POST",
      data: JSON.stringify({ template_id: template_id, values: values }),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (response, status, evn) {

        // Remove previous errors
        $('.is-invalid').removeClass('is-invalid');
        $('div.invalid-feedback').remove();

        if (response.errors)
        {
          // Show new errors
          let elements;
          response.errors.forEach(function (error) {

            console.log(error);

            //elements = document.querySelectorAll('[data-tpath="'+ error.path +'"]');
            elements = $('[data-tpath="' + error.path + '"]');

            console.log(elements);

            elements.each(function (index) {

              let element = $(this);

              element.addClass("is-invalid");

              element.append(`
                <div class="invalid-feedback d-block">${error.error}</div>
              `);
            });
          });
        }
      }
    });
  });
});