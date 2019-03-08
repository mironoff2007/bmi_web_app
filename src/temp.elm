module Temp exposing (..)

import Json.Decode exposing (Decoder, at, float, int, map3, string)

type alias Model =
    {
    person:Person
    }
type alias Person = { name : String, age : Int, height : Float }


person : Decoder Person
person =
  map3 Person
    (at ["name"] string)
    (at ["info","age"] int)
    (at ["info","height"] float)
