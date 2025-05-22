# IDATT2003 Boardgame Project 👍

This project was developed in tandem with the semesterproject in the course IDATT2003 Programming 2 at NTNU Trondheim.

We were tasked to create a virtual version of the classic snakes and ladders board game using Java, with JavaFX as the front-end framework of choiece. This project is the culmination of hours of hard work. 

## Core features 🚀

This program includes two virtual board games:
- **Ladder game**, a modern take on the classic snakes and ladders game.
- **Pario Marty**, a completely ""new"" board game experience.

This, although small, collection of board games allow for vastly different experiences every time you boot up the program.

### Ladder game 🪜

#### Boards ✅

The ladder game includes two predefined boards. One regular board, which is more or less identical to the classic snakes and ladders game, and a special board, which includes many new surprises in the form of new special tiles.

<img width="1194" alt="image" src="https://github.com/user-attachments/assets/4b097f8b-7970-4494-a216-d4e236d1bbbe" />

In addition to the two predifined board, you can also load your own custom boards from .json files! This means that you are able to create your very own experiences.

To create your own board, all you have to do is create a file (with the .json file extension) and follow the below format to create your own board:

```
{
  "tiles": [
    {
      "tileType": "NORMAL",
      "tileNumber": 1,
      "onscreenPosition": [
        0,
        9
      ]
    },
    {
      "tileType": "LADDER",
      "destinationTileNumber": 40,
      "tileNumber": 2,
      "onscreenPosition": [
        1,
        9
      ]
    },
    {
      "tileType": "RANDOM_ACTION",
      "tileNumber": 3,
      "onscreenPosition": [
        2,
        9
      ]
    },
    {
      "tileType": "ROLL_AGAIN",
      "tileNumber": 4,
      "onscreenPosition": [
        3,
        9
      ]
    },
    {
      "tileType": "RETURN_TO_START",
      "tileNumber": 5,
      "onscreenPosition": [
        4,
        9
      ]
    },
    {
      "tileType": "WINNER",
      "tileNumber": 6,
      "onscreenPosition": [
        5,
        9
      ]
    }
  ]
}
```

Above you can see how the different tiles are to be formatted in a custom .json file. There are also certain rules you will have to follow for the board is able to be loaded:
1. The board must be 90 tiles long.
2. The tiles must be in order of 1 - 90.
3. The first tile must be of the type "NORMAL".
4. The last tile must be of the type "WINNER".

You can also see that every tile has an attribute called "onscreenPosition", which more or less places the tile on the onscreen grid that contains the board. To ensure that these values are correct, please refer to the [demo board](https://github.com/siguraso/IDATT2003-Boardgame-Project/blob/main/src/main/resources/JSON/DemoBoard.json) as a template to make sure that it works as expected.

### Pario Marty 🥳

Pario Marty is the second game that is included in our collection of games, and is about collecting the most coins and crowns in 20 in-game turns to win the game. This game includes new tiles that are not featured in the ladder game.

<img width="1199" alt="image" src="https://github.com/user-attachments/assets/69a6015b-8044-45f7-9dd2-4c41a54b4cb0" />

### Players ♟️

Both board games can be played with up to four players, where each player can choose their own name as well as pick one of 8 unique player pieces. As an addition, you are also able to save the current players that have been entered to a .csv file on your computer, to quickly load it later on. This is especially useful if the same group of players often play together.

<img width="316" alt="image" src="https://github.com/user-attachments/assets/73a3f20d-90a1-4b63-a79b-2aa0d73b3f0c" />

### Dice 🎲

The user has the choice between using one or two dice. This means that you can both play the ladder game at it's, notoriously, slow pace, or you can play a very chaotic and fast paced round of pario marty.

<img width="398" alt="image" src="https://github.com/user-attachments/assets/fc6a9ff5-06e4-4e51-ae9f-18745e4ba2b3" />


### Helper windows ✌️

We know it sometimes can be very hard to understand the rules of a pario marty game. Or maybe you have never played snakes and ladders before. That's why we have implemented a helper window that explains the rules of both games. The helper window also changes based on what board you have chosen, so the helper window is more catered to your specific playthrough.

<img width="1091" alt="image" src="https://github.com/user-attachments/assets/41e5ca42-a216-44d4-a1ff-23357f7aa218" />

# Installation manual 📝

## Running the program 🏃

Heres how you can run the program:
- Download and install a version of Java (that is Java 21 or newer).
- Go to the [Releases](https://github.com/siguraso/IDATT2003-Boardgame-Project/releases) and download the .jar file from the newest version of the program.
- Simply run the file from your file explorer of choice!

# Running the JUnit-tests 🧪

**IMPORTANT**: The tests require that you are using a version of Java that is older than Java 22 because the file-handler-mocking plugin that we use for the tests require it.

There are two main ways of running the tests:

## Through the Maven CLI (Reccomended) 👌

To run the tests with the Maven CLI:
1. Download the source code
2. Download and install the (Maven Command Line Interface)[https://maven.apache.org/download.cgi]
3. Make sure that maven is configured with a Java 21 (or older) JDK!
4. Open a terminal window in the root directory
5. Type out the below Maven Command in the terminal:

```
mvn test
```

## Through an IDE 🧑‍💻

You can run the tests through an IDE (such as Intellij IDEA, Eclipse, VSCode, etc.), by following the below steps:
1. Download the source code
2. Open the root directory of the source code in your IDE of choice
3. Make sure the IDE has the source code configured as a Maven project
4. Simply run the JUnit tests!
