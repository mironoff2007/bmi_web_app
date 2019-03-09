import Browser
import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (..)
import Http
import Json.Decode exposing (Decoder, field, map2, string)
import Json.Decode exposing (Decoder, at, float, int, map3, string)


-- MAIN


main =
  Browser.element
    { init = init
    , update = update
    , subscriptions = subscriptions
    , view = view

    }



-- MODEL


type State
  = Failure
  | Loading
  | Success Bmi


type alias Model =
    { state : State
    , person : Bmi
    }


type alias Bmi = {
    bmi : Float,
    dateTime : String,
    weight : Int
    }

bmiDecoder : Decoder Bmi
bmiDecoder =
  map3 Bmi
    (field "bmi" float)
    (field "dateTime" string)
    (field "weight"  int)

init : () -> (State, Cmd Msg)
init _ =
  (Loading, getRandomCatGif)



-- UPDATE


type Msg
  = Load
  | GotBmi(Result Http.Error Bmi)


update : Msg -> State -> (State, Cmd Msg)
update msg model =
  case msg of
    Load ->
      (Loading, getRandomCatGif)

    GotBmi result ->
      case result of
        Ok bmi ->
          (Success bmi, Cmd.none)

        Err _ ->
          (Failure, Cmd.none)



-- SUBSCRIPTIONS


subscriptions : State -> Sub Msg
subscriptions state =
  Sub.none



-- VIEW

viewTableHeader : Html Msg
viewTableHeader =
    tr []
        [ th []
            [ text "BMI |" ]
        , th []
            [ text "| Date Time |" ]
        , th []
            [ text " Weight" ]
        ]

view : State -> Html Msg
view state =
  div []
    [ h2 [] [ text "table" ]
    , viewTableHeader
    , viewGif state
    ]

viewBmi :  Bmi -> Html Msg
viewBmi bmi =
    tr []
        [ td []
            [ text (Debug.toString bmi.bmi) ]
        , td []
            [ text bmi.dateTime ]
        , td []
            [ text (Debug.toString bmi.weight) ]
        ]

viewGif : State -> Html Msg
viewGif state =
  case state of
    Failure ->
      div []
        [ text "I could not load  "
        , button [ onClick Load ] [ text "Try Again!" ]
        ]

    Loading ->
      text "Loading..."

    Success bmi ->
      div []
        [ button [ onClick Load, style "display" "block" ] [ text "Load" ]
            , (viewBmi bmi)
        ]



-- HTTP


getRandomCatGif : Cmd Msg
getRandomCatGif =
  Http.get
    { url = "http://127.0.0.1:8080/bmi_web_app_war_exploded/Hello"
    , expect = Http.expectJson GotBmi bmiDecoder
    }





