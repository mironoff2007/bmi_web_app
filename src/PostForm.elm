port module PostForm exposing (main)

import Browser
import Html exposing (Html, button, div, input, text)
import Html.Attributes exposing (placeholder, value)
import Html.Events exposing (onClick, onInput)
import Http
import Json.Encode as JE
import Json.Decode as JD exposing (Decoder)



type alias Model =
    { result : Maybe (Result Http.Error ())
     ,inputW:String
     ,inputH:String
     ,inputN:String
    }

type alias User =
    { weight : Int
    , height : Int
    , name   : String
    }

init : () -> (Model, Cmd Msg)
init () =
    ({ result = Nothing , inputW="", inputH="", inputN=""}
    , Cmd.none
    )


type Msg
    = PostIt
    | GotIt (Result Http.Error ())
    | ChangeW String
    | ChangeH String
    | ChangeN String
    | ReceivedDataFromJS String
    | ReceivedW String

port receiveData : (String -> msg) -> Sub msg
port receiveW : (String -> msg) -> Sub msg

userDecoder : JD.Decoder User
userDecoder =
    JD.map3 User (JD.field "weight" JD.int) (JD.field "height" JD.int) (JD.field "name" JD.string)

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
    case msg of
        PostIt ->
            ({ model | result = Nothing }
            , Http.post
                { url = "http://127.0.0.1:8080/bmi_web_app_war_exploded/Hello"
                , body = Http.jsonBody <|
                    JE.object
                        [("weight", JE.int (String.toInt model.inputW|> Maybe.withDefault 0) )
                        , ("height",JE.int (String.toInt model.inputH|> Maybe.withDefault 0))
                        , ("name", JE.string  (model.inputN))
                        ]
                , expect = Http.expectWhatever GotIt
                }
            )

        GotIt result ->
            ({ model | result = Just result  }
            , Cmd.none
            )
        ChangeW str->(
              { model | inputW = str },Cmd.none
              )
        ChangeH str->(
               { model | inputH = str },Cmd.none
               )
        ChangeN str->(
                { model | inputN = str },Cmd.none
                 )
        ReceivedDataFromJS data ->(
              { model | inputN =  data },Cmd.none
              )
        ReceivedW data ->(
                  { model | inputW =  data },Cmd.none
                  )

view : Model -> Html Msg
view model =
    div []
        [ button [ onClick PostIt ] [ text "POST" ]
        , div [] [ text <|  if (Debug.toString model.result)=="Just (Err (BadStatus 415))"
                            then "Response: " ++"wrong number"
                            else if (Debug.toString model.result)=="Nothing"
                                 then ""
                                 else Debug.toString model.result ]
        , input [ placeholder "Weight",value model.inputW, onInput ChangeW ] []
        , div []  [input [ placeholder "Height",value model.inputH, onInput ChangeH ] []]
        , input [ placeholder "name",value model.inputN, onInput ChangeN ] []


        ]


subscriptions : Model-> Sub Msg
subscriptions model =
    receiveData ReceivedDataFromJS

subscriptions : Model-> Sub Msg
subscriptions model =
    receiveW ReceivedW

main : Program () Model Msg
main =Browser.element
    { init = init
    , view = view
    , update = update
    , subscriptions = subscriptions
    }
