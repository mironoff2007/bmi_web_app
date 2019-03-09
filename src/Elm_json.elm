import Browser
import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (..)
import Http
import Json.Decode as D
import Json.Decode exposing (Decoder, field, map2, string)
import Json.Decode exposing (Decoder, at, float, int, map3, string)
import FormatNumber.Locales exposing (Locale, frenchLocale, spanishLocale, usLocale)

import FormatNumber exposing (format)

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
  | Success (List Bmi)


type alias Model =
    { state : State
    , person : Bmi
    , bmis:List Bmi
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


bmiListDecoder : Decoder (List Bmi)
bmiListDecoder =
      D.list bmiDecoder

init : () -> (State, Cmd Msg)
init _ =
  (Loading, getRandomCatGif)



-- UPDATE


type Msg
  = Load
  | GotBmi(Result Http.Error (List Bmi))


update : Msg -> State -> (State, Cmd Msg)
update msg model =
  case msg of
    Load ->
      (Loading, getRandomCatGif)

    GotBmi result ->
      case result of
        Ok bmis ->
          (Success bmis, Cmd.none)

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
            [ text "BMI" ]
        , th []
            [ text "Date Time" ]
        , th []
            [ text "Weight" ]
        ]

view : State -> Html Msg
view state =
  div []
    [
    viewGif state
    ]


--Print Bmi fields (1 Row)
viewBmi :  Bmi -> Html Msg
viewBmi bmi =
    tr []
        [ td [Html.Attributes.style "text-align" "center" ]
            [ text ( (format (Locale 2 "," "." "−" "" "" "") bmi.bmi)++"") ]
        , td [Html.Attributes.style "text-align" "center" ]
            [ text (bmi.dateTime ++ "")]
        , td [Html.Attributes.style "text-align" "center" ]
            [ text (Debug.toString  bmi.weight++"") ]
        ]

viewBmis : List Bmi -> Html Msg
viewBmis bmis =
            div []
                [ h3 [] [ text "Table" ]
                , table []
                    ([ viewTableHeader ] ++ List.map viewBmi bmis)
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

    Success bmis ->
      div []
        [ button [ onClick Load, style "display" "block" ] [ text "Load" ]
            , (viewBmis bmis)
        ]



-- HTTP


getRandomCatGif : Cmd Msg
getRandomCatGif =
  Http.get
    { url = "http://127.0.0.1:8080/bmi_web_app_war_exploded/Hello"
    , expect = Http.expectJson GotBmi  bmiListDecoder
    }





