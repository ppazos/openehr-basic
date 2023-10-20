<html>
  <head>
    <meta name="layout" content="main"/>
    <script>
      var data   = ${( raw((params as grails.converters.JSON) as String) ) ?: '{}'};
      var errors = ${( raw((errors as grails.converters.JSON) as String) ) ?: '{}'};
      console.log(errors);
      
      $(function() {
         
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


        $('form').on('submit', function(e) {

          console.log('submit', this, this.action);

          e.preventDefault();

          let template_id;
          let values = [];

          $(':input').each(function(i) {

            if (this.tagName == 'BUTTON') return;

            // input with name is the template_id, other inputs will have data-* attributes with paths and archetype_id
            if (this.name && this.name == 'template_id')
            {
              template_id = this.value;
            }
            else if (Object.keys(this.dataset).length)
            {
              values.push(
                Object.assign({value: this.value}, this.dataset)
              );
            }

            console.log(this.dataset.tpath);

            //console.log(i, this.tagName, this.name, this, this.dataset, Object.keys(this.dataset).length);
          });

          console.log(values);

          $.ajax({
            url:         this.action,
            type:        "POST",
            data:        JSON.stringify({template_id: template_id, values: values}),
            contentType: "application/json; charset=utf-8",
            dataType:    "json",
            success: function(a, b, c) {
              console.log(a, b, c);
            }
          });

          // $.post(this.action, {template_id: template_id, values: values}, function(a, b, c) {
          //   console.log(a, b, c);
          // }, 'json')
          // .done(function(a, b, c) {
          //   console.log(a, b, c);
          // })
          // .fail(function(a, b, c) {
          //   console.log(a, b, c);
          // });

          return false;
        });
      });

    </script>
    <style>
    div {
      padding-left: 1em;
      margin-bottom: 0.5em;
    }
    .small {
      width: 56px;
    }
    .search {
      background-image: url("http://files.softicons.com/download/system-icons/crystal-project-icons-by-everaldo-coelho/png/32x32/apps/search.png");
      background-size: 16px 16px;
      background-repeat: no-repeat;
      width: 16px;
      height: 16px;
      display: inline-block;
    }
    </style>
  </head>
  <body>
    <div class="container">
      <g:form action="save_vitals" method="POST">
        <h1>Monitoreo de signos vitales</h1>
        <input type="hidden" name="template_id" value="Monitoreo de signos vitales" />
        <div class="OBSERVATION form-item">
          <span class="col">
            <label>Presion arterial</label>
          </span>
          <div class="EVENT form-item">
            <span class="col">
              <label>Cualquier evento</label>
            </span>
            <div class="ELEMENT form-group row form-item">
              <label class="col col-form-label">Sistolica</label>
              <div class="col">
                <input class="DV_QUANTITY form-control" type="number" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude" data-archetype="openEHR-EHR-OBSERVATION.presion_arterial.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude" />
              </div>
              <div class="col">
                <select class="DV_QUANTITY form-control" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/units" data-archetype="openEHR-EHR-OBSERVATION.presion_arterial.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/units">
                  <option value=""></option>
                  <option value="mm[Hg]">mm[Hg]</option>
                </select>
              </div>
            </div>
            <div class="ELEMENT form-group row form-item">
              <label class="col col-form-label">Diastolica</label>
              <div class="col">
                <input class="DV_QUANTITY form-control" type="number" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/magnitude" data-archetype="openEHR-EHR-OBSERVATION.presion_arterial.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/magnitude" />
              </div>
              <div class="col">
                <select class="DV_QUANTITY form-control" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/units" data-archetype="openEHR-EHR-OBSERVATION.presion_arterial.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/units">
                  <option value=""></option>
                  <option value="mm[Hg]">mm[Hg]</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="OBSERVATION form-item">
          <span class="col">
            <label>Frecuencia cardiaca</label>
          </span>
          <div class="EVENT form-item">
            <span class="col">
              <label>Cualquier evento</label>
            </span>
            <div class="ELEMENT form-group row form-item">
              <label class="col col-form-label">Medida de frecuencia cardiaca</label>
              <div class="col">
                <input class="DV_QUANTITY form-control" type="number" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude" data-archetype="openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude" />
              </div>
              <div class="col">
                <select class="DV_QUANTITY form-control" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/units" data-archetype="openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/units">
                  <option value=""></option>
                  <option value="/min">/min</option>
                </select>
              </div>
            </div>
            <div class="ELEMENT form-group row form-item">
              <label class="col col-form-label">Comentario</label>
              <textarea class="DV_TEXT form-control" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/value" data-archetype="openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value"></textarea>
            </div>
          </div>
        </div>
        <div class="OBSERVATION form-item">
          <span class="col">
            <label>Saturacion de oxigeno</label>
          </span>
          <div class="EVENT form-item">
            <span class="col">
              <label>Cualquier evento</label>
            </span>
            <div class="ELEMENT form-group row form-item">
              <label class="col col-form-label">SpO2</label>
              <div class="col-md-5">
                <label>numerator
                  <input type="number" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.saturacion_de_oxigeno.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/numerator" data-archetype="openEHR-EHR-OBSERVATION.saturacion_de_oxigeno.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/numerator" class="DV_PROPORTION form-control" />
                </label>
              </div>
              <div class="col-md-5">
                <label>denominator
                  <input type="number" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.saturacion_de_oxigeno.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/denominator" data-archetype="openEHR-EHR-OBSERVATION.saturacion_de_oxigeno.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/denominator" class="DV_PROPORTION form-control" />
                </label>
              </div>
            </div>
          </div>
        </div>
        <button type="submit" name="save" class="btn btn-primary text-right">Save</button>
      </g:form>
    </div>
  </body>
</html>