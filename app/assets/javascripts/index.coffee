$ ->
  $.get "/listLanguages", (data) ->
    $.each data, (i, language) ->
      $("#languages").append "<li>" + language.name + ", " + language.nameEnglish + ", " + language.code + "</li>"