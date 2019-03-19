port module GetTable exposing (main)

import Browser
import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (..)
import Http
import Json.Decode as D
import Json.Encode as JE
import Json.Decode exposing (Decoder, field, string)
import Json.Decode exposing (Decoder, float, int, map3, string)
import FormatNumber.Locales exposing (Locale)

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
  | GetReq String


type Msg
  = GotBmi(Result Http.Error (List Bmi))
  | ReceivedURL String



type alias Model =
    { state : State
    , person : Bmi
    , bmis:List Bmi
    , str:String
    , url:String
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
  (Loading, Cmd.none)



-- UPDATE





port receiveUrl : (String -> msg) -> Sub msg

-- SUBSCRIPTIONS
subscriptions : State-> Sub Msg
subscriptions state =
    receiveUrl ReceivedURL


update : Msg -> State -> (State, Cmd Msg)
update msg model =
  case msg of


    GotBmi result ->
      case result of
        Ok bmis ->
          (Success bmis, Cmd.none)

        Err _ ->
          (Failure, Cmd.none)

    ReceivedURL url->(GetReq url, upload url)











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
    viewBmiTable state
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

viewBmiTable : State -> Html Msg
viewBmiTable state =
  case state of
    Failure ->
      div []
        [ text "I could not load  "
        ]

    Loading ->
      div [][ text "Loading  "]


    Success bmis ->
      div []
        [  (viewBmis bmis)]
    GetReq str->text str



-- HTTP



upload : String -> Cmd Msg
upload url =
  Http.request
    { method = "GET"
    , headers = []
    , url = (url++"servlet")
    , body = Http.emptyBody
    , expect = Http.expectJson GotBmi  bmiListDecoder
    , timeout = Nothing
    , tracker = Nothing
    }




