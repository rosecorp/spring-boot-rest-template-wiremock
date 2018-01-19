# spring-boot-rest-template-wiremock


Example spring boot application where embedded wiremock acts as third party rest service. Call happens on:

``http://localhost:8080/app`` and controller is calling gateway which calls third party endpoint on:
``http:/localhost:8089/endpoint``