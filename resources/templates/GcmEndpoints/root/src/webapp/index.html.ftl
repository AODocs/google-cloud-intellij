<!DOCTYPE html>
<html>
<head>
    <title>Hello, Google Cloud Messaging!</title>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" ></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</head>
<body role="document" style="padding-top: 70px;">
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Hello, Google Cloud Messaging!</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Documentation <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="https://developers.google.com/appengine/docs/java/">Google App Engine</a></li>
                        <li><a href="https://developers.google.com/appengine/docs/java/endpoints/">Google Cloud Endpoints</a></li>
                        <li><a href="http://developer.android.com/google/gcm/">Google Cloud Messaging</a></li>
                        <li><a href="https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints">Connecting your Android application to this backend</a></li>
                    </ul>
                </li>
                <li><a href="/_ah/api/explorer">Google Cloud Endpoints API Explorer</a></li>
                <li><a href="https://console.developers.google.com">Google Developers Console</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="container theme-showcase" role="main">
    <!--
      Output from GCM call.
    -->
    <div class="alert alert-success" style="visibility: collapse;" id="outputAlert"></div>

    <!--
      A form that takes a message text and submits it to "messaging" Endpoints API,
      access to this Endpoints API is enabled once the client is loaded below.
    -->
    <div class="jumbotron">
        <div class="row">
            <div class="col-lg-12">
                <h1>Hello, Google Cloud Messaging!</h1>
                <p>Enter your message below and press "Send Message" button to send it over Google Cloud Messaging to all registered devices.</p>
                <form>
                    <div class="input-group">
                        <input type="text" class="form-control input-lg" placeholder="Message text" id="messageTextInput" />
                          <span class="input-group-btn">
                             <button class="btn btn-default btn-primary btn-group btn-lg" type="submit" id="sendMessageButton">Send Message &raquo;</button>
                          </span>
                    </div>
                </form>
                <br/>
                <p>If you need step-by-step instructions for connecting your Android application to this backend module, see <a href="https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints">"App Engine Backend with Google Cloud Messaging" template documentation</a>.</p>
                <p>
                    <small>
                        For more information about Google App Engine for Java, check out the <a href="https://developers.google.com/appengine/docs/java/">App Engine documentation</a>.<br />
                        To learn more about Google Cloud Endpoints, see <a href="https://developers.google.com/appengine/docs/java/endpoints/">Cloud Endpoints documentation</a>.<br />
                        Similarly, for more information about Google Cloud Messaging, see <a href="http://developer.android.com/google/gcm/">Cloud Messaging documentation</a>.<br />
                        If you'd like to access your generated Google Cloud Endpoints APIs directly, see the <a href="/_ah/api/explorer">Cloud Endpoints API Explorer</a>.
                    </small>
                </p>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    // A function that attaches a "Send Message" button click handler
    function enableClick() {
      document.getElementById('sendMessageButton').onclick = function() {
        var message = document.getElementById('messageTextInput').value;
        if (!message) {
          message = '(Empty message)';
        }

        gapi.client.messaging.messagingEndpoint.sendMessage({'message': message}).execute(
          function(response) {
            var outputAlertDiv = document.getElementById('outputAlert');
            outputAlertDiv.style.visibility = 'visible';

            if (response && response.error) {
              outputAlertDiv.className = 'alert alert-danger';
              outputAlertDiv.innerHTML = '<b>Error Code:</b> ' + response.error.code + ' [' + response.error.message +']';
            }
            else {
              outputAlertDiv.className = 'alert alert-success';
              outputAlertDiv.innerHTML = '<b>Success:</b> Message \"' + message + '\" sent to all registered devices!</h2>';
            }
          }
        );
        return false;
      }
    }

    // This is called initially
    function init() {
      var apiName = 'messaging'
      var apiVersion = 'v1'
      // set the apiRoot to work on a deployed app and locally
      var apiRoot = '//' + window.location.host + '/_ah/api';
      var callback = function() {
        enableClick();
      }
      gapi.client.load(apiName, apiVersion, callback, apiRoot);
    }
  </script>
<!--
 Load the Google APIs Client Library for JavaScript
 More info here : https://developers.google.com/api-client-library/javascript/reference/referencedocs
-->
<script src="https://apis.google.com/js/client.js?onload=init"></script>
</body>
</html>
