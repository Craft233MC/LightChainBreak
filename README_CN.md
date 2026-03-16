# LightChainBreak

一个简单的用于连锁破坏的 Minecraft Paper/Folia 插件。

[[EN]](README.md)

## 功能特性

- **连锁挖矿** - 支持多种矿石的连锁采集
- **连锁砍树** - 一键砍伐整棵树
- **高度可配置** - 支持自定义工具、方块类型和权限组
- **最小耐久度** - 可以让物品留有最后1耐久, 而非报废损坏
- **数据存储** - 支持 YAML、MySQL、MariaDB、SQLite 多种存储方式
- **领地兼容** - 支持 Residence 领地插件
- **PlaceholderAPI** - 支持玩家设置状态变量
- **MiniMessage** - 支持丰富的文本格式

## 支持版本

- **Minecraft**: 1.20.1+
- **服务端**: Paper / Folia
- **Java**: 17+

## 安装

1. 从 [Releases](https://github.com/Craft233MC/LightChainBreak/releases) 下载最新版本
2. 将插件放入服务器的 `plugins` 文件夹
3. 重启服务器
4. 编辑 `plugins/LightChainBreak/config.yml` 进行配置

## 指令

| 指令 | 描述 | 权限 |
|------|------|------|
| `/lightchainbreak` 或 `/lcb` | 主指令 | - |
| `/lcb toggle` | 切换连锁开关 | `lightchainbreak.toggle` |
| `/lcb reload` | 重载配置文件 | `lightchainbreak.reload` |
| `/lcb about` | 显示关于信息 | `lightchainbreak.about` |
| `/lcb help` | 显示帮助信息 | `lightchainbreak.help` |

## 权限

- `lightchainbreak.toggle` - 允许使用切换指令 (默认: 所有人)
- `lightchainbreak.reload` - 允许重载配置 (默认: OP)
- `lightchainbreak.about` - 允许查看关于信息 (默认: OP)
- `lightchainbreak.help` - 允许查看帮助 (默认: OP)
- `lightchainbreak.*` - 所有权限 (默认: OP)

## 配置

### 连锁组配置

插件预设了四种连锁组：
- `mine` - 矿石（镐子 + 矿石）
- `wood` - 原木（斧头 + 原木）
- `leaves` - 树叶 (剪刀 + 树叶)
- `wart_block` - 疣块 (锄头 + 疣块)

每个组可以配置：
- 权限节点
- 允许的工具列表
- 可连锁破坏的方块列表

### 数据存储配置

支持以下存储方式：
- **YAML** - 本地文件存储
- **MySQL** - 远程数据库存储
- **MariaDB** - 远程数据库存储
- **SQLite** - 本地数据库存储

## 依赖

**必需:**
- Paper/Folia 1.20.1+

**可选:**
- PlaceholderAPI - 变量支持
- Residence- 领地
