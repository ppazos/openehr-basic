<!doctype html>
<html lang="en" class="no-js">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
      <g:layoutTitle default="openEHR basic"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

    <asset:stylesheet src="application.css"/>
    
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" />

    <g:layoutHead/>
  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark navbar-static-top" role="navigation">
      <div class="container-fluid">
        <a class="navbar-brand" href="/#"><asset:image src="grails.svg" alt="Grails Logo"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" aria-expanded="false" style="height: 0.8px;" id="navbarContent">
          <ul class="nav navbar-nav ml-auto">
            <g:pageProperty name="page.nav"/>
          </ul>
        </div>
      </div>
    </nav>

    <g:layoutBody/>

    <div class="footer" role="contentinfo">
      <div class="container-fluid">
        <div class="row">
          <div class="col">
            <%-- <a href="http://guides.grails.org" target="_blank">
                <asset:image src="advancedgrails.svg" alt="Grails Guides" class="float-left"/>
            </a>
            <strong class="centered"><a href="http://guides.grails.org" target="_blank">Grails Guides</a></strong>
            <p>Building your first Grails app? Looking to add security, or create a Single-Page-App? Check out the <a href="http://guides.grails.org" target="_blank">Grails Guides</a> for step-by-step tutorials.</p> --%>
          </div>
          <div class="col">
            
          </div>
        </div>
      </div>
    </div>

    <div id="spinner" class="spinner" style="display:none;">
      <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

  </body>
</html>
