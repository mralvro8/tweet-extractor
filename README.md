# tweet-extractor

Proyecto de prueba api de twitter usando twitter4j.

Los endpoints son:

| URL                              |	TIPO  |       INPUT    |	OUPUT                            |
|:---------------------------------|:------:|:--------------:|:---------------------------------:|
| /extractor/tweets	  	           | GET	  |                | List<TweetOuput> output           |
| /extractor/tweets/{idioma}       | GET	  | String idioma	 | List<TweetOuput> output           |
| /extractor/validar/{id}	         | POST   | int id	       | TweetOuput                        |
| /extractor/getValidados/{usuario}| GET    |	String usuario |	List<TweetOuput> output          |
| /extractor/listHashtags          | GET	  |	               | List<String> lista de hashtags    |
| /extractor/listHashtags/{tam}    | GET	  | int tam lista	 | List<String> lista de hashtags    |
  
  Ej: _localhost:8080/extractor-bff/extractor/listHashtags/10_
  
  El objeto de salida TweetOuput tiene la siguiente estructura:
  
  {
        "id": int,
        "texto":String,
        "usuario":String,
        "idioma":String,
        "validado":boolean,
        "localizacion":String
    }
