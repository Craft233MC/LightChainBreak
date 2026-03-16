# LightChainBreak
A simple Minecraft Paper/Folia plugin for chain breaking/mining.

[[CN]](README_CN.md)

## Features
- **Chain Mining** - Supports chain collection of various ores
- **Tree Felling** - One-click to chop down entire trees
- **Highly Configurable** - Supports custom tools, block types, and permission groups
- **Minimum Durability** - Keep tools at 1 durability instead of breaking
- **Data Storage** - Supports YAML, MySQL, MariaDB, and SQLite
- **Residence Compatible** - Supports Residence protection plugin
- **PlaceholderAPI** - Supports player status variables
- **MiniMessage** - Supports rich text formatting

## Supported Versions
- **Minecraft**: 1.20.1+
- **Server**: Paper / Folia
- **Java**: 17+

## Installation
1. Download the latest version from [Releases](https://github.com/Craft233MC/LightChainBreak/releases)
2. Place the plugin in your server's `plugins` folder
3. Restart the server
4. Edit `plugins/LightChainBreak/config.yml` to configure

## Commands
| Command | Description | Permission |
|------|------|------|
| `/lightchainbreak` or `/lcb` | Main command | - |
| `/lcb toggle` | Toggle chain breaking on/off | `lightchainbreak.toggle` |
| `/lcb reload` | Reload configuration file | `lightchainbreak.reload` |
| `/lcb about` | Show about information | `lightchainbreak.about` |
| `/lcb help` | Show help information | `lightchainbreak.help` |

## Permissions
- `lightchainbreak.toggle` - Allow using toggle command (Default: everyone)
- `lightchainbreak.reload` - Allow reloading config (Default: OP)
- `lightchainbreak.about` - Allow viewing about info (Default: OP)
- `lightchainbreak.help` - Allow viewing help (Default: OP)
- `lightchainbreak.*` - All permissions (Default: OP)

## Configuration

### Chain Group Configuration
The plugin comes with four preset chain groups:

- `mine` - Ores (Pickaxe + Ores)
- `wood` - Logs (Axe + Logs)
- `leaves` - Leaves (Shears + Leaves)
- `wart_block` - Wart Blocks (Hoe + Wart Blocks)

Each group can be configured with:

- Permission node
- Allowed tools list
- Chain breakable blocks list

### Data Storage Configuration
The following storage methods are supported:

- **YAML** - Local file storage
- **MySQL** - Remote database storage
- **MariaDB** - Remote database storage
- **SQLite** - Local database storage

## Dependencies
**Required:**
- Paper/Folia 1.20.1+

**Optional:**
- PlaceholderAPI - Variable support
- Residence - Residence protection