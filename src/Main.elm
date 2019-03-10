module Main_inc_decr exposing (main)

import Browser
import Html exposing (Html, button, div, text)
import Html.Events exposing (onClick)
import Http


type alias Model =
    {
     count : Int,
     str:String
    }


initialModel : Model
initialModel =
    { count = 0,str=""}


type Msg
    = Increment
    | Decrement
    | Method
    | GotText (Result Http.Error String)

getPublicOpinion : Cmd Msg
getPublicOpinion =
  Http.get
    { url = "https://elm-lang.org/assets/public-opinion.txt"
    , expect = Http.expectString GotText
    }

update : Msg -> Model -> Model
update msg model =
    case msg of
        Increment ->
            { model | count = model.count + 1 }

        Decrement ->
            { model | count = model.count - 1 }
        Method ->
                { model | str=Debug.toString getPublicOpinion }
        GotText(Ok str)  ->
                    ( { model| str = str})
        GotText (Err httpError) ->
            ( { model| str=Debug.toString httpError})
view : Model -> Html Msg
view model =
    div []
        [ button [ onClick Increment ] [ text "+1" ]
        , div [] [ text <| String.fromInt model.count ]
        , button [ onClick Decrement ] [ text "-1" ]
        , button [ onClick Method ] [ text "method" ]
        , div [] [ text <|  model.str ]
        ]


main : Program () Model Msg
main =
    Browser.sandbox
        { init = initialModel
        , view = view
        , update = update
        }
