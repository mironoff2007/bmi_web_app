module PostForm exposing (main)

import Browser
import Html exposing (Html, button, div, text)
import Html.Events exposing (onClick)
import Http
import Json.Encode as JE
import Json.Decode as JD


type alias Model =
    { result : Maybe (Result Http.Error ())
    }

type alias User =
    { weight : Float
    , height : Float
    }

init : () -> (Model, Cmd Msg)
init () =
    ({ result = Nothing }
    , Cmd.none
    )


type Msg
    = PostIt
    | GotIt (Result Http.Error ())

userDecoder : JD.Decoder User
userDecoder =
    JD.map2 User (JD.field "weight" JD.float) (JD.field "height" JD.float)

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
    case msg of
        PostIt ->
            ({ model | result = Nothing }
            , Http.post
                { url = "http://127.0.0.1:8080/bmi_web_app_war_exploded/Hello"
                , body = Http.jsonBody <|
                    JE.object
                        [("weight", JE.float 99.9)
                        , ("height", JE.float 199.9)
                        ]
                , expect = Http.expectWhatever GotIt
                }
            )

        GotIt result ->
            ({ model | result = Just result }
            , Cmd.none
            )


view : Model -> Html Msg
view model =
    div []
        [ button [ onClick PostIt ] [ text "POST" ]
        , div [] [ text <| "Response: " ++ Debug.toString model.result ]
        ]


main : Program () Model Msg
main =
    Browser.element
        { init = init
        , view = view
        , update = update
        , subscriptions = always Sub.none
        }
