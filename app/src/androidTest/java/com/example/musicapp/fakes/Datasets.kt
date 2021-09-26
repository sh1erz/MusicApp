package com.example.musicapp.fakes

import com.example.musicapp.data.entities.Album
import com.example.musicapp.data.entities.Artist
import com.example.musicapp.data.entities.Track

val test_album = Album(
    0,
    "Album name",
    null,
    null,
    "https://api.deezer.com/album/302127/image",
    "https://api.deezer.com/album/302127/image",
    "https://api.deezer.com/album/302127/image",
    "https://api.deezer.com/album/302127/image",
    "https://api.deezer.com/album/302127/image",
    "0",
    null,
    null,
    null
)
val test_artist = Artist(
    27,
    "Name Surname",
    "https://www.deezer.com/artist/27",
    null,
    "https://api.deezer.com/artist/27/image",
    "https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/56x56-000000-80-0-0.jpg",
    "https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/250x250-000000-80-0-0.jpg",
    "https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/500x500-000000-80-0-0.jpg",
    "https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/1000x1000-000000-80-0-0.jpg",
    null,
    null,
    "https://api.deezer.com/artist/27/top?limit=50"
)

val TRACKS_DATASET = listOf(
    Track(
        27,
        0,
        "Title 1",
        "https://www.deezer.com/track/3135556",
        null,
        100,
        null,
        null,
        "https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-8.mp3",
        null,
        test_artist,
        test_album,
        null
    ),
    Track(20,
        1,
        "Title 2",
        "https://www.deezer.com/track/3135556",
        null,
        100,
        null,
        null,
        "https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-8.mp3",
        null,
        test_artist,
        test_album,
        null),
    Track(28,
        3,
        "Title 3",
        "https://www.deezer.com/track/3135556",
        null,
        100,
        null,
        null,
        "https://cdns-preview-d.dzcdn.net/stream/c-deda7fa9316d9e9e880d2c6207e92260-8.mp3",
        null,
        test_artist,
        test_album,
        null)
)
