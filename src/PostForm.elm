port module PostForm exposing (main)

import Browser
import Html exposing (Html,  div,  text)
import Http
import Json.Encode as JE
import Json.Decode as JD exposing (Decoder)



type alias Model =
    { result : Maybe (Result Http.Error ())
     ,inputW:String
     ,inputH:String
     ,inputN:String
     ,url:String
    }

type alias User =
    { weight : Int
    , height : Int
    , name   : String
    }

init : () -> (Model, Cmd Msg)
init () =
    ({ result = Nothing , inputW="", inputH="", inputN="",url=""}
    , Cmd.none
    )


type Msg
    = 
      GotIt (Result Http.Error ())
    | ReceivedN String
    | ReceivedW String
    | ReceivedH String
    | ReceivedURL String
    | Loading

port receiveN_last : (String -> msg) -> Sub msg
port receiveW : (String -> msg) -> Sub msg
port receiveH : (String -> msg) -> Sub msg
port receiveUrl : (String -> msg) -> Sub msg

userDecoder : JD.Decoder User
userDecoder =
    JD.map3 User (JD.field "weight" JD.int) (JD.field "height" JD.int) (JD.field "name" JD.string)

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
    case msg of
        ReceivedN name ->
            ({ model | result = Nothing }
            , Http.request
                           { method = "POST"
                           , headers = [(Http.header "Access-Control-Allow-Origin" (model.url++"servlet"))
                                       ,(Http.header "Access-Control-Allow-Methods" "POST")]
                           , url = model.url++"servlet"
                           , body = Http.jsonBody <|
                                    JE.object
                                    [("weight", JE.int (String.toInt model.inputW|> Maybe.withDefault 0) )
                                      , ("height",JE.int (String.toInt model.inputH|> Maybe.withDefault 0))
                                      , ("name", JE.string  (name))
                                     ]
                           , expect = Http.expectWhatever GotIt
                           , timeout = Nothing
                           , tracker = Nothing
                           }

            )

        GotIt result ->
            ({ model | result = Just result  }
            , Cmd.none
            )

        
        ReceivedW w ->(
                  { model | inputW =  w },Cmd.none
                  )
        ReceivedH h ->(
                  { model | inputH =  h },Cmd.none
                   )

        ReceivedURL url ->(
                        { model | url =  url },Cmd.none
                       )
        Loading ->(model,Cmd.none)

view : Model -> Html Msg
view model =
    div []
        [ div [] [ text <|  if (Debug.toString model.result)=="Just (Err (BadStatus 415))"
                                    then "Response: " ++"wrong number"
                                    else if (Debug.toString model.result)=="Nothing"
                                         then ""
                                         else if (Debug.toString model.result)=="Just (Ok ())"
                                                then "Ok"
                                                else Debug.toString model.result ]

        ]

subscriptions : Model-> Sub Msg
subscriptions _ =Sub.batch[
    receiveN_last ReceivedN,
    receiveW ReceivedW,
    receiveH ReceivedH,
    receiveUrl ReceivedURL
    ]

main : Program () Model Msg
main =Browser.element
    { init = init
    , view = view
    , update = update
    , subscriptions =subscriptions
    }
