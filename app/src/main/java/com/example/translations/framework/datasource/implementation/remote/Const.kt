package com.example.translations.framework.datasource.implementation.remote

//https://cloud.yandex.com/en-ru/docs/translate/api-ref/Translation/translate#https-request
//POST
const val API_URL               = "https://translate.api.cloud.yandex.net/translate/v2/"
const val CONNECT_TIMEOUT       = 120L
const val READ_TIMEOUT          = 120L

const val IAM_TOKEN             = "t1.9euelZqRio2ejZLNmpuMlJSQnsuOlu3rnpWaz5LNycmWnZrNkJ7HjouNjZXl8_dYHTBt-e8Jb3s7_d3z9xhMLW357wlvezv9.cgQ_ImI2uwoTgwSE-4ms4rn2uYB9Zp18kdC9T1XnRJDwV_LnxZUrESTXgX2EnGuUe23C561Nofw9AP1tQDp-DA"
const val KEY_ID                = "ajej0nmr09jd8noe2bpe"
const val API_KEY               = "AQVN0wXtsCZtRfDhLy7AVLmEfC-8yHXpw74XL_YG"

object Headers {
    const val CONTENT_TYPE      = "Content-Type: Application/Raw"
    const val ACCEPT            = "Accept: application/json;charset=utf-8"
    const val API_KEY_HEADER    = "Authorization: Api-Key $API_KEY"
}

const val LANGUAGES             = "languages"
const val TRANSLATE             = "translate"