port module PostForm exposing (main)

import Browser

import Html.Events exposing (onInput)
import Html.Events exposing (onClick)
import Html exposing (..)
import Html.Attributes exposing (..)


import Http
import Json.Encode as JE

import Http
import Json.Decode as D

import Json.Decode exposing (Decoder, field, string)
import Json.Decode exposing (Decoder, float, int, map5, string)
import FormatNumber.Locales exposing (Locale)

import FormatNumber exposing (format)
import ParseInt exposing (Error)
import Time exposing (Month(..), toDay, toHour, toMinute, toMonth, toSecond, toYear, utc)
import Time

import Validate exposing (Validator, ifBlank, ifNotInt, validate)
type alias Model =
    {
     result : Maybe (Result Http.Error ())
    ,inputW:String
    ,inputH:String
    ,inputN:String
    ,url:String
    ,bmis:List Bmi
    }
type alias Bmi = {
    name:String,
    bmi : Float,
    dateTimeStep : Int,
    weight : Int,
    height : Int
    }

bmiDecoder : Decoder Bmi
bmiDecoder =
    map5 Bmi
        (field "name" string)
        (field "bmi" float)
        (field "dateTimeStep" int)
        (field "weight" int)
        (field "height" int)

bmiListDecoder : Decoder (List Bmi)
bmiListDecoder =
    D.list bmiDecoder

--Month to Int decoder
toIntMonth : Month -> String
toIntMonth month =
  case month of
    Jan -> "1"
    Feb -> "2"
    Mar -> "3"
    Apr -> "4"
    May -> "5"
    Jun -> "6"
    Jul -> "7"
    Aug -> "8"
    Sep -> "9"
    Oct -> "10"
    Nov -> "11"
    Dec -> "12"

--Date Time to String converter
toUtcString : Time.Posix -> String
toUtcString time =
  String.fromInt (toHour utc time)
  ++ ":" ++
  addZero (toMinute utc time)
  ++ ":" ++
  addZero (toSecond utc time)
  ++ " | " ++
  String.fromInt (toDay utc time)
  ++ "/" ++
  (toIntMonth (toMonth utc time))
   ++ "/" ++
  String.fromInt (toYear utc time)

--add zero before number
addZero : Int -> String
addZero int =if int<10 then ("0"++String.fromInt(int))else String.fromInt(int)


init : () -> (Model, Cmd Msg)
init () =
    ({ result = Nothing , inputW="", inputH="", inputN="",url="",bmis=[]}, Cmd.none)

type Msg
    = PostIt
    | PostResult (Result Http.Error ())
    | GotBmi(Result Http.Error (List Bmi))
    | GetInputN String
    | GetInputW String
    | GetInputH String
    | ReceivedURL String



port receiveUrl : (String -> msg) -> Sub msg


update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
    case msg of
    GetInputN name ->({ model | inputN = name }, Cmd.none)

    GetInputW w ->({ model | inputW = w },Cmd.none)

    GetInputH h ->({ model | inputH = h },Cmd.none)

    ReceivedURL url ->({ model | url = url },upload model.url)

    PostResult result -> ({ model | result = Just result },upload model.url)

    GotBmi result ->
        case result of
            Ok bmis ->( {model | bmis = bmis}, Cmd.none)

            Err _ ->(model, Cmd.none)
    PostIt ->( {model| result = Nothing},post model)


view : Model -> Html Msg
view model =

            div[]
            [
            input [ placeholder "Name",value model.inputN, onInput GetInputN ][],
             br[][],
             input [ placeholder "Weight",value model.inputW, onInput GetInputW ][],
             br[][],
             input [ placeholder "Height",value model.inputH, onInput GetInputH ][],
             br[][],
             viewValidation model,
             button [ onClick PostIt ] [ text "POST" ],
             br[][],
             text <| if (Debug.toString model.result)=="Just (Err (BadStatus 415))"
                        then "Response: " ++"wrong number"
                        else if (Debug.toString model.result)=="Nothing"
                        then ""
                         else if (Debug.toString model.result)=="Just (Ok ())"
                         then "Ok"
                         else Debug.toString model.result
                         ,viewBmis model.bmis

             ]


viewValidation : Model -> Html msg
viewValidation model =
  if (ParseInt.parseInt(model.inputH)|> Result.withDefault 0) <=0 then
    div [ style "color" "red" ] [ text "Wrong Height" ]
  else if (ParseInt.parseInt(model.inputW)|> Result.withDefault 0) <=0 then
    div [ style "color" "red" ] [ text "Wrong Weight" ]
  else
     div[][]



-- VIEW

viewTableHeader : Html Msg
viewTableHeader =
    tr []
    [
    th []
    [ text " Name " ]
    , th []
    [ text " BMI " ]
    , th []
    [ text " Date Time " ]
    , th []
    [ text " Weight " ]
    , th []
    [ text " Height " ]
    ]



--Print Bmi fields (1 Row)
viewBmi : Bmi -> Html Msg
viewBmi bmi =
    tr [][
        td [Html.Attributes.style "text-align" "center" ]
        [ text bmi.name ]
        ,td [Html.Attributes.style "text-align" "center" ]
        [ text ( (format (Locale 2 "," "." "−" "" "" "") bmi.bmi)) ]
        ,td [Html.Attributes.style "text-align" "center" ]
        [ text (toUtcString( bmi.dateTimeStep|>Time.millisToPosix))]
        ,td [Html.Attributes.style "text-align" "center" ]
        [ text (Debug.toString bmi.weight) ]
        ,td [Html.Attributes.style "text-align" "center" ]
        [ text (Debug.toString bmi.height) ]
    ]

viewBmis : List Bmi -> Html Msg
viewBmis bmis =
    div []
    [ h3 [] [ text "Table" ]
    , table []
    ([ viewTableHeader ] ++ List.map viewBmi bmis)
    ]

subscriptions : Model-> Sub Msg
subscriptions _ =receiveUrl ReceivedURL
    

main : Program () Model Msg
main =Browser.element
    { init = init
    , view = view
    ,update = update
    , subscriptions =subscriptions
    }


upload : String -> Cmd Msg
upload url =
    Http.request
    { method = "GET"
    , headers = []
    , url = (url++"servlet")
    , body = Http.emptyBody
    , expect = Http.expectJson GotBmi bmiListDecoder
    , timeout = Nothing
    , tracker = Nothing
    }

post : Model-> Cmd Msg
post  model  =  Http.request
                { method = "POST"
                    , headers = []
                    , url = model.url++"servlet"
                    , body = Http.jsonBody <|
                       JE.object
                        [("weight", JE.int (String.toInt model.inputW|> Maybe.withDefault 0) )
                        ,("height",JE.int (String.toInt model.inputH|> Maybe.withDefault 0))
                        ,("name", JE.string (model.inputN))
                        ]
                    , expect = Http.expectWhatever PostResult
                    , timeout = Nothing
                    , tracker = Nothing
                }