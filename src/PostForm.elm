module PostForm exposing (main)

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
    }

type alias User =
    { weight : Int
    , height : Int
    , id     : Int
    }

init : () -> (Model, Cmd Msg)
init () =
    ({ result = Nothing , inputW=""}
    , Cmd.none
    )


type Msg
    = PostIt
    | GotIt (Result Http.Error ())
    | Change String

userDecoder : JD.Decoder User
userDecoder =
    JD.map3 User (JD.field "weight" JD.int) (JD.field "height" JD.int) (JD.field "id" JD.int)

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
                        , ("height", JE.int 180)
                        , ("id", JE.int 1)
                        ]
                , expect = Http.expectWhatever GotIt
                }
            )

        GotIt result ->
            ({ model | result = Just result }
            , Cmd.none
            )
        Change str->(
              { model | inputW = str },Cmd.none
              )





view : Model -> Html Msg
view model =
    div []
        [ button [ onClick PostIt ] [ text "POST" ]
        , div [] [ text <| "Response: " ++ Debug.toString model.result ]
        ,input [ value model.inputW, onInput Change ] []


        ]


main : Program () Model Msg
main =
    Browser.element
        { init = init
        , view = view
        , update = update
        , subscriptions = always Sub.none
        }
