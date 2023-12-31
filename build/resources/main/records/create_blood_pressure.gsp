<html>
  <head>
    <meta name="layout" content="main"/>
    <script>
      var data   = ${( raw((params as grails.converters.JSON) as String) ) ?: '{}'};
      var errors = ${( raw((errors as grails.converters.JSON) as String) ) ?: '{}'};
      console.log(errors);
      
      $(function() {
         
        var controls = $(':input');
        for (i=0; i<controls.length; i++)
        {
          console.log(controls[i].name);
          
          // show data
          for (path in data)
          {
            if (controls[i].name == path)
            {
              $(controls[i]).val( data[path] );
            }
          }
          
          // show errors
          for (path in errors)
          {
            console.log(path);
            
            // field has error?
            if (controls[i].name.startsWith( path ))
            {
              $(controls[i]).parent().addClass('has-error');
            }
          }
        }


        $('form').on('submit', function(e) {

          console.log('submit');

          e.preventDefault();

          $(':input').each(function(i){

            console.log(this, this.dataset);
          });

          return false;
        });
      });

    </script>
    <style>
    div {
       padding: 10px;
    }
    .small {
        width: 48px;
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
      <h1>openEHR-EHR-OBSERVATION.blood_pressure.v1</h1>
      <g:form action="save_blood_pressure" method="POST">
        <input type="hidden" name="archetypeId" value="openEHR-EHR-OBSERVATION.blood_pressure.v1" />
        <div class="OBSERVATION">
          <label>Blood Pressure</label>
          <div class="HISTORY">
            <label>history</label>
            <div class="EVENT">
              <label>any event</label>
              <div class="ITEM_TREE">
                <label>blood pressure</label>
                <div class="ELEMENT">
                  <label>Systolic</label>
                  <input type="text" name="/data[at0001]/events[at0006]/data[at0003]/items[at0004]/value/magnitude" class="form-control" />
                  <select name="/data[at0001]/events[at0006]/data[at0003]/items[at0004]/value/units" class="form-control">
                    <option value="mm[Hg]">mm[Hg]</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Diastolic</label>
                  <input type="text" name="/data[at0001]/events[at0006]/data[at0003]/items[at0005]/value/magnitude" class="form-control" />
                  <select name="/data[at0001]/events[at0006]/data[at0003]/items[at0005]/value/units" class="form-control">
                    <option value="mm[Hg]">mm[Hg]</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Mean Arterial Pressure</label>
                  <input type="text" name="/data[at0001]/events[at0006]/data[at0003]/items[at1006]/value/magnitude" class="form-control" />
                  <select name="/data[at0001]/events[at0006]/data[at0003]/items[at1006]/value/units" class="form-control">
                    <option value="mm[Hg]">mm[Hg]</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Pulse Pressure</label>
                  <input type="text" name="/data[at0001]/events[at0006]/data[at0003]/items[at1007]/value/magnitude" class="form-control" />
                  <select name="/data[at0001]/events[at0006]/data[at0003]/items[at1007]/value/units" class="form-control">
                    <option value="mm[Hg]">mm[Hg]</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Comment</label>
                  <textarea name="/data[at0001]/events[at0006]/data[at0003]/items[at0033]/value" class="form-control"></textarea>
                </div>
              </div>
              <div class="ITEM_TREE">
                <label>state structure</label>
                <div class="ELEMENT">
                  <label>Position</label>
                  <select name="/data[at0001]/events[at0006]/state[at0007]/items[at0008]/value/defining_code" class="form-control">
                    <option value="at1000">Standing</option>
                    <option value="at1001">Sitting</option>
                    <option value="at1002">Reclining</option>
                    <option value="at1003">Lying</option>
                    <option value="at1014">Lying with tilt to left</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Confounding factors</label>
                  <textarea name="/data[at0001]/events[at0006]/state[at0007]/items[at1052]/value" class="form-control"></textarea>
                </div>
                <div class="ELEMENT">
                  <label>Sleep status</label>
                  <select name="/data[at0001]/events[at0006]/state[at0007]/items[at1043]/value/defining_code" class="form-control">
                    <option value="at1044">Alert &amp; awake</option>
                    <option value="at1045">Sleeping</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Tilt</label>
                  <input type="text" name="/data[at0001]/events[at0006]/state[at0007]/items[at1005]/value/magnitude" class="form-control" />
                  <select name="/data[at0001]/events[at0006]/state[at0007]/items[at1005]/value/units" class="form-control">
                    <option value="°">°</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="INTERVAL_EVENT">
              <label>24 hour average </label>
              <div class="DV_CODED_TEXT">
                <label />
                <select name="/data[at0001]/events[at1042]/math_function/defining_code" class="form-control">
                  <option value="146" />
                </select>
              </div>
              <div class="DV_DURATION">
                <label />
              </div>
              <div class="ITEM_TREE">
                <label>blood pressure</label>
                <div class="ELEMENT">
                  <label>Systolic</label>
                  <input type="text" name="/data[at0001]/events[at1042]/data[at0003]/items[at0004]/value/magnitude" class="form-control" />
                  <select name="/data[at0001]/events[at1042]/data[at0003]/items[at0004]/value/units" class="form-control">
                    <option value="mm[Hg]">mm[Hg]</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Diastolic</label>
                  <input type="text" name="/data[at0001]/events[at1042]/data[at0003]/items[at0005]/value/magnitude" class="form-control" />
                  <select name="/data[at0001]/events[at1042]/data[at0003]/items[at0005]/value/units" class="form-control">
                    <option value="mm[Hg]">mm[Hg]</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Mean Arterial Pressure</label>
                  <input type="text" name="/data[at0001]/events[at1042]/data[at0003]/items[at1006]/value/magnitude" class="form-control" />
                  <select name="/data[at0001]/events[at1042]/data[at0003]/items[at1006]/value/units" class="form-control">
                    <option value="mm[Hg]">mm[Hg]</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Pulse Pressure</label>
                  <input type="text" name="/data[at0001]/events[at1042]/data[at0003]/items[at1007]/value/magnitude" class="form-control" />
                  <select name="/data[at0001]/events[at1042]/data[at0003]/items[at1007]/value/units" class="form-control">
                    <option value="mm[Hg]">mm[Hg]</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Comment</label>
                  <textarea name="/data[at0001]/events[at1042]/data[at0003]/items[at0033]/value" class="form-control"></textarea>
                </div>
              </div>
              <div class="ITEM_TREE">
                <label>state structure</label>
                <div class="ELEMENT">
                  <label>Position</label>
                  <select name="/data[at0001]/events[at1042]/state[at0007]/items[at0008]/value/defining_code" class="form-control">
                    <option value="at1000">Standing</option>
                    <option value="at1001">Sitting</option>
                    <option value="at1002">Reclining</option>
                    <option value="at1003">Lying</option>
                    <option value="at1014">Lying with tilt to left</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Confounding factors</label>
                  <textarea name="/data[at0001]/events[at1042]/state[at0007]/items[at1052]/value" class="form-control"></textarea>
                </div>
                <div class="ELEMENT">
                  <label>Sleep status</label>
                  <select name="/data[at0001]/events[at1042]/state[at0007]/items[at1043]/value/defining_code" class="form-control">
                    <option value="at1044">Alert &amp; awake</option>
                    <option value="at1045">Sleeping</option>
                  </select>
                </div>
                <div class="ELEMENT">
                  <label>Tilt</label>
                  <input type="text" name="/data[at0001]/events[at1042]/state[at0007]/items[at1005]/value/magnitude" class="form-control" />
                  <select name="/data[at0001]/events[at1042]/state[at0007]/items[at1005]/value/units" class="form-control">
                    <option value="°">°</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="ITEM_TREE">
            <label>Tree</label>
            <div class="ELEMENT">
              <label>Cuff size</label>
              <select name="/protocol[at0011]/items[at0013]/value/defining_code" class="form-control">
                <option value="at0015">Adult Thigh</option>
                <option value="at0016">Large Adult</option>
                <option value="at0017">Adult</option>
                <option value="at1008">Small Adult</option>
                <option value="at1009">Paediatric/Child</option>
                <option value="at1018">Infant</option>
                <option value="at1019">Neonatal</option>
              </select>
            </div>
            <div class="CLUSTER">
              <label>Location</label>
              <div class="ELEMENT">
                <label>Location of measurement</label>
                <select name="/protocol[at0011]/items[at1033]/items[at0014]/value/defining_code" class="form-control">
                  <option value="at0025">Right arm</option>
                  <option value="at0026">Left arm</option>
                  <option value="at0027">Right thigh</option>
                  <option value="at0028">Left thigh</option>
                  <option value="at1020">Right wrist</option>
                  <option value="at1021">Left wrist</option>
                  <option value="at1026">Right ankle</option>
                  <option value="at1031">Left ankle</option>
                  <option value="at1032">Finger</option>
                  <option value="at1051">Toe</option>
                  <option value="at1053">Intra-arterial</option>
                </select>
              </div>
              <div class="ELEMENT">
                <label>Specific location</label>
                <textarea name="/protocol[at0011]/items[at1033]/items[at1034]/value" class="form-control"></textarea>
              </div>
            </div>
            <div class="ELEMENT">
              <label>Method</label>
              <select name="/protocol[at0011]/items[at1035]/value/defining_code" class="form-control">
                <option value="at1036">Auscultation</option>
                <option value="at1037">Palpation</option>
                <option value="at1039">Machine</option>
                <option value="at1040">Invasive</option>
              </select>
            </div>
            <div class="ELEMENT">
              <label>Mean Arterial Pressure Formula</label>
              <textarea name="/protocol[at0011]/items[at1038]/value" class="form-control"></textarea>
            </div>
            <div class="ELEMENT">
              <label>Diastolic endpoint</label>
              <select name="/protocol[at0011]/items[at1010]/value/defining_code" class="form-control">
                <option value="at1011">Phase IV</option>
                <option value="at1012">Phase V</option>
              </select>
            </div>
          </div>
        </div>
        <input type="submit" name="save" value="Submit" />
      </g:form>
    </div>
  </body>
</html>