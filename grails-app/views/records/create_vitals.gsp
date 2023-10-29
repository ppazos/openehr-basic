<html>
  <head>
    <meta name="layout" content="main"/>
    <script>
      var data   = ${( raw((params as grails.converters.JSON) as String) ) ?: '{}'};
      var errors = ${( raw((errors as grails.converters.JSON) as String) ) ?: '{}'};

      console.log(errors);
    </script>
    <asset:javascript src="create"/>
    <style>
    div {
      padding-left: 1em;
      margin-bottom: 0.5em;
    }
    div.ELEMENT {
      padding-left: 1em;
      margin-bottom: 0em;
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
    .is-invalid {
      border: 1px solid rgb(220, 53, 69);
      padding: 0.5em;
    }
    </style>
  </head>
  <body>
    <div class="container">
      <g:form action="save_vitals" method="POST">
        <h1>Monitoreo de signos vitales</h1>
        <input type="hidden" name="template_id" value="Monitoreo de signos vitales" />
        <div class="OBSERVATION form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]">
          <span class="col">
            <label>Presion arterial</label>
          </span>
          <div class="EVENT form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]">
            <span class="col">
              <label>Cualquier evento</label>
            </span>
            <div class="ELEMENT form-group row form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]">
              <label class="col col-form-label">Sistolica</label>
              <div class="DV_QUANTITY form-group row form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value">
                <div class="col">
                  <input class="DV_QUANTITY form-control" type="number" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude" data-archetype="openEHR-EHR-OBSERVATION.presion_arterial.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude" />
                </div>
                <div class="col">
                  <select class="DV_QUANTITY form-select" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/units" data-archetype="openEHR-EHR-OBSERVATION.presion_arterial.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/units">
                    <option value=""></option>
                    <option value="mm[Hg]">mm[Hg]</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="ELEMENT form-group row form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]">
              <label class="col col-form-label">Diastolica</label>
              <div class="DV_QUANTITY form-group row form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value">
                <div class="col">
                  <input class="DV_QUANTITY form-control" type="number" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/magnitude" data-archetype="openEHR-EHR-OBSERVATION.presion_arterial.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/magnitude" />
                </div>
                <div class="col">
                  <select class="DV_QUANTITY form-select" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/units" data-archetype="openEHR-EHR-OBSERVATION.presion_arterial.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/units">
                    <option value=""></option>
                    <option value="mm[Hg]">mm[Hg]</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="OBSERVATION form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]">
          <span class="col">
            <label>Frecuencia cardiaca</label>
          </span>
          <div class="EVENT form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]">
            <span class="col">
              <label>Cualquier evento</label>
            </span>
            <div class="ELEMENT form-group row form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]">
              <label class="col col-form-label">Medida de frecuencia cardiaca</label>
              <div class="DV_QUANTITY form-group row form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value">
                <div class="col">
                  <input class="DV_QUANTITY form-control" type="number" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude" data-archetype="openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude" />
                </div>
                <div class="col">
                  <select class="DV_QUANTITY form-select" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/units" data-archetype="openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/units">
                    <option value=""></option>
                    <option value="/min">/min</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="ELEMENT form-group row form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]">
              <label class="col col-form-label">Comentario</label>
              <div class="DV_TEXT form-group row form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value">
                <textarea class="DV_TEXT form-control" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value/value" data-archetype="openEHR-EHR-OBSERVATION.frecuencia_cardiaca.v1" data-path="/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value"></textarea>
              </div>
            </div>
          </div>
        </div>
        <div class="OBSERVATION form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.saturacion_de_oxigeno.v1]">
          <span class="col">
            <label>Saturacion de oxigeno</label>
          </span>
          <div class="EVENT form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.saturacion_de_oxigeno.v1]/data[at0001]/events[at0002]">
            <span class="col">
              <label>Cualquier evento</label>
            </span>
            <div class="ELEMENT form-group row form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.saturacion_de_oxigeno.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]">
              <label class="col col-form-label">SpO2</label>
              <div class="DV_PROPORTION form-group row form-item" data-tpath="/content[archetype_id=openEHR-EHR-OBSERVATION.saturacion_de_oxigeno.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0005]/value">
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
        </div>
        <button type="submit" name="save" class="btn btn-primary text-right">Save</button>
      </g:form>
    </div>
  </body>
</html>