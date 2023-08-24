<%@ page import="grails.converters.JSON" %>
<!-- HTML for static distribution bundle build -->
<!DOCTYPE html>
<%@ page import="grails.converters.JSON" contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Swagger UI</title>
    <asset:stylesheet src="swagger-ui.css"/>

    <asset:link rel="icon" type="image/png" href="favicon-32x32.png" />
    <asset:link rel="icon" type="image/png" href="favicon-16x16.png" sizes="16x16" />
    
    <style>
    html
    {
        box-sizing: border-box;
        overflow: -moz-scrollbars-vertical;
        overflow-y: scroll;
    }

    *,
    *:before,
    *:after
    {
        box-sizing: inherit;
    }

    body
    {
        margin:0;
        background: #fafafa;
    }
    </style>
</head>

<body>
<div id="swagger-ui"></div>
<g:javascript src="swagger-ui-bundle.js"/>
<g:javascript src="swagger-ui-standalone-preset.js"/>
<g:javascript>
    window.onload = function() {
        // Begin Swagger UI call region
        const ui = SwaggerUIBundle({
            url: "<%= raw(url) %>",
            dom_id: '#swagger-ui',
            deepLinking: true,
            presets: [
                SwaggerUIBundle.presets.apis,
                SwaggerUIStandalonePreset
            ],
            plugins: [
                SwaggerUIBundle.plugins.DownloadUrl
            ],
            layout: "StandaloneLayout"
        });
        // End Swagger UI call region

        window.ui = ui;
    };
</g:javascript>
</body>
</html>
