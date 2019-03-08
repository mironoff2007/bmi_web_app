module Manifest exposing (..)  -- remove this line to run in http://elm-lang.org/try



import Html exposing (Html, node, div, h2, text, table, thead, tbody, tr, th, td)
import Html.Attributes exposing (class)

import Json.Decode as Json exposing (Decoder)


main : Html msg
main =
  let
    manifest =
      Json.decodeString manifestDecoder manifestData
  in
    case manifest of
      Err msg ->
            msg

      Ok pages ->
        div [ class "container" ] <|
          [ node "style" [] [ text cssStyles ] ] ++ (List.map pageView pages)


pageView : Page -> Html msg
pageView (Page title widget) =
  let
    userRow user =
      tr []
        [ td [] [ text user.id ]
        , td [] [ text user.name ]
        , td [] [ text user.email ]
        ]

    accountRow account =
      tr []
        [ td [] [ text account.id ]
        , td [] [ text account.name ]
        , td [] [ text account.owner.id ]
        , td [] [ text account.owner.name ]
        , td [] [ text account.owner.email ]
        ]

    widgetView =
      case widget of
        Users users ->
          table []
            [ thead []
              [ tr []
                [ th [] [ text "ID" ]
                , th [] [ text "Name" ]
                , th [] [ text "Email" ]
                ]
              ]
            , tbody [] (List.map userRow users)
            ]

        Accounts accounts ->
          table []
            [ thead []
              [ tr []
                [ th [] [ text "ID" ]
                , th [] [ text "Name" ]
                , th [] [ text "Owner ID" ]
                , th [] [ text "Owner Name" ]
                , th [] [ text "Owner Email" ]
                ]
              ]
            , tbody [] (List.map accountRow accounts)
            ]
  in
    div []
      [ h2 [] [ text title ]
      , widgetView
      ]


manifestDecoder : Decoder Manifest
manifestDecoder =
  Json.list pageDecoder


pageDecoder : Decoder Page
pageDecoder =
  Json.field "primaryWidget" Json.string
    |> Json.andThen pageHelp


pageHelp : String -> Decoder Page
pageHelp widgetType =
  case widgetType of
    "user" ->
      Json.map2 Page
        (Json.field "pageTitle" Json.string)
        (Json.at ["primaryWidgetData", "data"] <| Json.map Users <| Json.list userDecoder)

    "account-manager" ->
      Json.map2 Page
        (Json.field "pageTitle" Json.string)
        (Json.at ["primaryWidgetData", "accounts"] <| Json.map Accounts <| Json.list accountDecoder)

    _ ->
      Json.fail <|
        "Cannot parse page with widget type \"" ++ widgetType ++ "\""


userDecoder : Decoder User
userDecoder =
  Json.map3 User
    (Json.field "id" Json.string)
    (Json.field "name" Json.string)
    (Json.field "email" Json.string)


accountDecoder : Decoder Account
accountDecoder =
  Json.map3 Account
    (Json.field "accountId" Json.string)
    (Json.field "accountName" Json.string)
    (Json.field "owner" userDecoder)


type alias Manifest = List Page


type Page = Page String Widget


type Widget
  = Users (List User)
  | Accounts (List Account)


type alias User =
  { id : String
  , name : String
  , email : String
  }


type alias Account =
  { id : String
  , name : String
  , owner : User
  }


manifestData : String
manifestData =
  """
  [
    {
      "pageTitle": "users",
      "primaryWidget": "user",
      "primaryWidgetData": {
        "permissions": ["read", "write"],
        "data": [
          {
            "name": "name 1",
            "email": "someemail@sd.cs",
            "id": "dsd33-wdsds"
          },
          {
            "name": "name 2",
            "email": "another@sd.cs",
            "id": "0000-wdsds"
          }
        ]
      }
    },
    {
      "pageTitle": "accounts",
      "primaryWidget": "account-manager",
      "primaryWidgetData": {
        "accounts": [
          {
            "accountId": "id 1",
            "accountName": "acc name",
            "owner": {
              "name": "name 2",
              "email": "another@sd.cs",
              "id": "0000-wdsds"
            }
          },
          {
            "accountId": "id 2",
            "accountName": "acc name 2",
            "owner": {
              "name": "name 1",
              "email": "someemail@sd.cs",
              "id": "dsd33-wdsds"
            }
          }
        ]
      }
    }
  ]
  """


cssStyles : String
cssStyles =
  """
  * { box-sizing: "border-box"; }
  html, body {
    margin: 0;
    padding: 0;
    font: 100%/1.4 -apple-system, "Helvetica Neue", Helvetica, Arial, sans-serif;
    color: #333;
  }
  .container {
    max-width: 40rem;
    margin: 2rem auto;
  }
  .container table {
    border-collapse: collapse;
    table-layout: fixed;
  }
  .container table th,
  .container table td {
    width: 1%;
    padding: 0.25rem;
  }
  .container table th {
    text-align: left;
  }
  .container table thead tr {
    border-bottom: 3px solid #e8e8e8;
  }
  .container table tbody tr + tr {
    border-top: 1px solid #c8c8c8;
  }
  """