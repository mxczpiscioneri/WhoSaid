package br.com.piscioneri.whosaid.data

data class Phrase(
    val answers: List<Answer>? = null,
    val id: String = "",
    val image: String = "",
    val source: Source? = null,
    val text: String = ""
)

//{
//    "phrases": [
//    {
//        "id": "1",
//        "text": "A polícia só bate em quem tem que bater.",
//        "image": "https://spotniks.com/wp-content/uploads/2017/11/42-48313063.w710.h473.2x.jpg",
//        "source": {
//        "name": "O Globo",
//        "description": "A frase foi dita por Lula no dia 7 de outubro de 2010, durante a cerimônia de evento de batismo da plataforma P-57, em um estaleiro em Angra dos Reis.",
//        "url": "https://oglobo.globo.com/brasil/eleicoes-2010/no-rio-lula-diz-que-agora-policia-bate-em-quem-tem-que-bater-4986471"
//    },
//        "answers": [
//        {
//            "id": 1,
//            "text": "Lula",
//            "right": true
//        },
//        {
//            "id": 2,
//            "text": "Bolsonaro",
//            "right": false
//        },
//
//        ]
//    },
//    {
//        "id": "2",
//        "text": "Os economistas que dizem entender muito de economia são os mesmos que levaram o Brasil para o buraco.",
//        "image": "https://spotniks.com/wp-content/uploads/2017/11/PLANO-REAL_equipe.jpg",
//        "source": {
//        "name": "Estadão",
//        "description": "A frase foi dita por Bolsonaro no último mês de setembro a uma reportagem.",
//        "url": "https://politica.estadao.com.br/noticias/geral,o-antipetista-com-ideias-de-esquerda,70001963892"
//    },
//        "answers": [
//        {
//            "id": 1,
//            "text": "Lula",
//            "right": false
//        },
//        {
//            "id": 2,
//            "text": "Bolsonaro",
//            "right": true
//        },
//
//        ]
//    }
//    ]
//}