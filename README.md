<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]


<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Best-README-Template</h3>

  <p align="center">
    An awesome README template to jumpstart your projects!
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Report Bug</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]]

Polite Messaging (PM) is a TCP-based peer-to-peer, asynchronous messaging system in which peers store and exchange messages.

The main purpose of PM is to store and exchange messages. Each message consists of four or more lines.
These lines are interpreted as headers until the Contents header has been found. All lines after that are interpreted as body.

Message Headers:
* Message-id: SHA-256 hash
This MUST be the first header. The hash MUST be the SHA-256 sum of the rest of the headers and the body of the message.
* Time-sent: time
The time is when the message was created.
* From: person
This identifies who sent the message. person MAY be an e-mail address.
* Contents: number
This MUST be the last header. The number gives how many lines of the body follow.
* To: person
This identifies who the message is going to. person MAY be an e-mail address.
* Subject: subject
This gives the what the message is about.
* Topic: topic
This gives the general topic of the message. topic MAY be a hash-tag


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple example steps.

### Compilation

1. Clone the repo
   ```sh
   git clone https://github.com/Acortes02/PoliteMessaging.git
   ```
2. Open up terminal and execute `make` to compile the project
   ```sh
   make
   ```

<!-- USAGE EXAMPLES -->
## Usage

1. Open up two separate terminals and run the `main.java` on both terminals using
   ```sh
   java main
   ```
2. Select option `2` on one terminal to listen for incoming connections

3. Select option `1` on the other terminal to connect to the listener

### Protocol Requests & Replies

`TIME?` Ask the peer for current time
`GET? SHA256sum` Get message using its message-ID
`SAVE`	Save the message list to `msgBackup.txt` for persistent storage
`LOAD`	Load the message list from `msgBackup.txt` for persistent storage


<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/Acortes02/PoliteMessaging/issues) for a list of proposed features (and known issues).


<!-- LICENSE -->
## License

None.



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/Acortes02/PoliteMessaging/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/Acortes02/PoliteMessaging/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/Acortes02/PoliteMessaging/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/Acortes02/PoliteMessaging/issues
[product-screenshot]: images/screenshot.png