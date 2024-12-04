package tictactoe

import java.util.*
import kotlin.math.*

fun gameState(list: MutableList<MutableList<String>>, char: String) = when {
        list[0][0] == char && list[0][1] == char && list[0][2] == char -> true
        list[0][0] == char && list[1][0] == char && list[2][0] == char -> true
        list[2][0] == char && list[2][1] == char && list[2][2] == char -> true
        list[0][2] == char && list[1][2] == char && list[2][2] == char -> true
        list[0][0] == char && list[1][1] == char && list[2][2] == char -> true
        list[0][2] == char && list[1][1] == char && list[2][0] == char -> true
        list[0][1] == char && list[1][1] == char && list[2][1] == char -> true
        else -> false
}

fun countCharacters(list: MutableList<MutableList<String>>, char: String): Int {
    var count = 0
    for (i in list) {
        for (j in i) {
            if (j == char) {
                count++
            }
        }
    }
    return count
}

fun displayGame(list: MutableList<MutableList<String>>) {
    println("---------")
    println("| ${list[0][0]} ${list[0][1]} ${list[0][2]} |")
    println("| ${list[1][0]} ${list[1][1]} ${list[1][2]} |")
    println("| ${list[2][0]} ${list[2][1]} ${list[2][2]} |")
    println("---------")
}

fun findWinner(game: MutableList<MutableList<String>>): String {
    // check the state of the game
    val hasXWon = gameState(game, "X")
    val hasOWon = gameState(game, "O")
    val numberOfX = countCharacters(game, "X")
    val numberOfO = countCharacters(game, "O")

    return when  {
        (hasXWon && hasOWon || (abs(numberOfX - numberOfO) >= 2)) -> "Impossible"
        hasXWon && !hasOWon && (" " !in game[0] || " " !in game[1] || " " !in game[2]) -> "X wins"
        hasOWon && !hasXWon && (" " !in game[0] || " " !in game[1] || " " !in game[2]) -> "O wins"
        !hasXWon && !hasOWon && (" " in game[0] || " " in game[1] || " " in game[2]) -> "Game not finished"
        !hasXWon && !hasOWon && (" " !in game[0] || " " !in game[1] || " " !in game[2]) -> "Draw"
        else -> ""
    }
}

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val game = mutableListOf(
        mutableListOf(" ", " ", " "),
        mutableListOf(" ", " ", " "),
        mutableListOf(" ", " ", " ")
    )
    // display initial state
    displayGame(game)
    var winner = ""
    var whichPlayer = 1 // will be used to determine which character to place in list, when even it's "O" else "X"
    var xWon = false
    var oWon = false
    do {
        // get coordinates from the user
        val (strX1, strX2) = readln().split(" ")
        if (!strX1.first().isDigit() || !strX2.first().isDigit()) {
            println("You should enter numbers!")
            continue
        }

        // convert the coordinates to integers
        var x = strX1.toInt()
        var y = strX2.toInt()

        // validate coordinates
        when {
            (x !in 1..3) || (y !in 1..3) -> {
                println("Coordinates should be from 1 to 3!")
                continue
            }
            game[x - 1][y - 1] != " " -> {
                println("This cell is occupied! Choose another one!")
                continue
            }
            else -> {
                // check if even or odd, update to O when even and X when odd
                game[x - 1][y - 1] = if (whichPlayer % 2 == 1) "X" else "O"
                // increment whichPlayer
                whichPlayer++
            }
        }
        displayGame(game)
        xWon = gameState(game, "X")
        oWon = gameState(game, "O")
        // check if coordinates are va
        if (xWon || oWon || (xWon && oWon)) break
        // display game state
    } while (true)
    // display the winner
    println(findWinner(game))
}