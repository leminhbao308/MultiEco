# MultiEco Plugin

## English

### Description

MultiEco is a versatile Bukkit plugin designed to manage multiple in-game currencies for your Minecraft server. With MultiEco, you can easily create, modify, and track various virtual currencies, providing a dynamic and engaging economy system for your players.

### Features

- Create and manage multiple in-game currencies.
- Customize the icon for each currency to give them unique visual representation.
- Automatically save and load player currency balances.
- Easily integrate with other economy-related plugins through the Vault API.
- Simple and user-friendly commands for administrators to manage currencies.

### Installation

1. Download the MultiEco plugin JAR file from the [releases page](https://github.com/leminhbao308/MultiEco).
2. Place the JAR file in your server's `plugins` directory.
3. Restart or reload your server.

### Usage

#### Eco Commands

```

  /eco balance : Check your balance
  /eco balance <player> : Check specified player's balance

```

```

  /eco give <player> <currencyID> <amount> : Give player amount of specified currency
  /eco set <player> <currencyID> <amount> : Set player balance of specified currency
  /eco take <player> <currencyID> <amount> : Take player amount of specified currency

```

```

  /eco admin create <currencyID> : Create new currency
  /eco admin delete <currencyID> : Delete specified currency
  /eco admin iconset <currencyID> <newIcon> : Set new icon for specified currency
  /eco admin icondel <currencyID> : Delete current icon for specified currency

```


#### Pay Command

```

  /pay <player> <currencyID> <amount> : Pay amount of your specified currency to target

```


### Configuration

You can configure the plugin by editing the `config.yml` file. This file allows you to define the details of each currency, such as its name, icon, and default balance.

### Support

For any issues or questions, please create a [new issue](https://github.com/leminhbao308/MultiEco/issues) on the GitHub repository.

### Contributing

There's currently no contributor available now.

### License

This plugin is licensed under the [MIT License](LICENSE).
