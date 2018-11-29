# Code-X Reference Guide

>欢迎使用Code-X  
>关注官方网站:【http://www.codex.pub】  
>关注Github:【https://github.com/codex-league/codex】

[toc]

## 1 我们能做什么？
>Code-X致力解决团队开发中的各种效率问题，目前我们能做的是解决部分重复性工作，比如CRUD工作的重复性，以及restfulAPI文档编写一致性；  
从目前看来Code-X希望减少程序员的重复性开发工作，把更多的尽力放在业务实现，或者更有价值的工作当中。

## 2 环境要求
> jdk 1.8 +  
> MySql (后续支持其他关系型数据库或非关系型数据库)  
> maven 、gradle  
> spring boot 2.x

## 3 快速开始
### 3.1 引入依赖

> gradle：
```Groovy
    compile("pub.codex:codex-index:2.1.1")
```
> maven:
```xml
<parent>
    <groupId>pub.codex</groupId>
    <artifactId>codex-index</artifactId>
    <version>2.1.1</version>
</parent>
     
```

### 3.2 配置信息

>在spring boot 引口类增加 `@EnableCodexLeague`   
这么做表示当前项目正式启用`Code-X`，与此同时`Code-X`会与当前项目配置信息做绑定
```java

@SpringBootApplication
@@EnableCodexLeague // 添加codex启用信息
public class TestApplication { 

    public static void main(String[] args) {
       ……
    }
}
```
>只是这么做还不够，你还需要告诉`Code-X`一些基础配置信息，下面列出必须的简单配置信息，详细解释见后续

>在spring boot `application.yml`，添加一下信息
```yml
codex:
  jdbc:
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: 123456
    driverClassName: com.mysql.jdbc.Driver
  package:
    entity-path: pub.codex.db.entity
    mapper-path: pub.codex.db.mapper
    mapperXML-path: mapper
    service-path: pub.codex.db.service
    serviceImpl-path: pub.codex.db.service.impl
  prefix: t_,tb_,test_
apix:
  controllerPackage: pub.codex.controller

```

>完成以上配置后，你可以你的启动项目，如果见到下列图标，恭喜你成功了

```

                           _                 ___     ___
      ______              | |                \  \   /  /
   . /  ____'  ____    ___| |  ____           \  \ /  / __ _ _
 /\\ | '      / __ \  / __` | / __ \   ______  \  _  /  \ \ \ \
( ( )| |     | |  | || |  | || /__\_, (______) /     \   \ \ \ \
 \\/ | .____ | |__| || |__| || |____          /  / \  \   ) ) ) )
  '  \______' \____/  \___, | \____/         /__/   \__\ / / / /
 =======================================================/_/_/_/


本项目使用Code-x
关注官方网站:【http://www.codex.pub】
关注Github:【https://github.com/codex-league/codex】

```
>你可以访问工具地址 http://{IP}:{port}/codex.html  
例如：http://localhost:8080/codex.html

:::info
IP: 是你的项目地址  
port: 是你项目的端口
:::

## 4 CRUD生成
>利用`Code-X`可实现CRUD重复性工作快速生成   
我们再来看一下配置文件：  
- jdbc:设置你的的数据库信息
- package.entity-path: 设置你的entity的位置
- package.mapper-path: 设置你的mapper的位置
- package.service-path: 设置你的service的位置
- package.serviceImpl-path: 设置你的service实现的位置
- package.mapperXML-path: 设置你的mapper.xml的位置
- prefix: 忽略表的前缀(可添加多个 按逗号分割)

```yml
codex:
  jdbc:
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: 123456
    driverClassName: com.mysql.jdbc.Driver
  package:
    entity-path: pub.codex.db.entity
    mapper-path: pub.codex.db.mapper
    mapperXML-path: mapper
    service-path: pub.codex.db.service
    serviceImpl-path: pub.codex.db.service.impl
  prefix: t_,tb_,test_

```
>配置好上面的信息，就可以完全使用 CRUD生成的功能了  

>如果你只是单独配置了code-core，你可以访问http://localhost:8080/codex-core.html


## 5 api文档工具

>api文档工具，可以使你快捷的生成文档信息  
你只需要你在的Spring boot 配置文件中增加下列配置：

- controllerPackage: 描述你的resetful接口的包路径，这样codex可以根据你指定的范围进行搜索API，并生成文档信息


```yml
apix:
  controllerPackage: pub.codex.controller
```

>如果你只是单独配置了apix，你可以访问http://localhost:8080/codex-apix.html


### 5.1 原理

>codex运行时会去根据用户提供的`controllerPackage`包路径来进行restful api扫描，扫描的接口信息必须是String boot 的RequestHandler信息

```java
@Api("演示API管理接口描述")  // 只描述Controller 信息
@RestController
public class DemoApix {

   
    @ApiOperation(value = "演示接口描述") // 只描述API信息
    @PostMapping("index")
    private Person index(@RequestBody Person person, 
            @RequestParam String name) {

        return person;
    }

}

```

>上面是一个简单接口描述信息，`Apix`会根据 `@RequestBody`和 `@RequestParam `来确参数的详细内容

>如果你想为你的api文档丰富起来,你可以使用更多的注解



注解 | 描述
---|---
@Api | 描述你的Controller信息
@ApiOperation | 描述你的接口信息
@ApiParam| 描述你 @RequestParam的参数信息
@ApiModelProperty| 描述你的@RequestBody的对象字段信息
@ApiGroup | 描述你的@RequestBody对象字段信息分组
@Valid|设置你的@RequestBody的对象字段信息全部必填
@Validated|设置你的@RequestBody的对象字段信息的组来设置必填
@NotBlank、@NotNull..|设置的验证方式 与@Validated搭配使用


>看了上面的那么多注解，应该已经晕了，由于注解组合较多，关系比较复杂，下面为你娓娓道来

>上述注解总体可以分两大类，一类是Apix的自有注解，例如：@Api、@ApiOperation、@ApiParam、@ApiModelProperty、@ApiGroup
>另一类是外部支持注解，例如：@Valid、@Validated、@NotBlank、@NotNull  


>自有注解主要作为工作描述接口的描述信息;  
而外部支持注解主要工作是设置接口强验证，`Apix`利用外部支持注解来为文档提供必填信息

:::info
关于外部支持，可以参考
http://hibernate.org/validator/  
详细了解@Valid、@Validated、@NotBlank、@NotNull..  本文档不在详细描述他们的功能
:::







### 5.2 添加描述信息
对@RequestBody对象字段描述时，由于@Valid、@Validated和@ApiGroup的作用范围不同，会表现出不同的优先级，所以存在众多注解组合，为了避免不同注解间相互影响，采用以下思路来解决：

>规定：
>- @Valid、@Validated和@ApiGroup作用于@RequestBody的对象，@NotBlank、@NotNull..等和@ApiModelProperty注解作用于对象字段。
>- @Valid、@Validated的优先级大于@ApiGroup
>- @NotBlank、@NotNull..等优先级大于@ApiModelProperty
>- @ApiModelProperty包含两种形式，分别是@ApiModelProperty("注释")和@ApiModelProperty("注释",group)，以下描述会省略为@ApiModelProperty和@ApiModelProperty(group)
>- 以下描述中(group)表示当前注解有分组信息，注意在进行注解组合时，如若分组信息不匹配，当前注解也会失效
- 当存在@Valid，所有包含分组的注解失效，所有对象字段中存在@NotBlank、@NotNull..等注解则为必填，存在@ApiModelProperty则为选填,如若两者同时存在则以优先级较高的为准。当@ApiModelProperty不存在或者失效，只存在@NotBlank、@NotNull..等时，则当前字段的注释以该字段名代替。
- @Validated(group)作用域包括@NotBlank(group)、@NotNull(group)..等和@ApiModelProperty(group)，并且先校验@NotBlank(group)、@NotNull(group)..等注解，存在则为必填，其次才会校验和@ApiModelProperty(group)，存在则为选填，如若两者同时存在则以优先级较高的为准。当@ApiModelProperty(group)不存在或者失效，只存在@NotBlank(group)、@NotNull(group)..等时，则当前字段的注释以该字段名代替。
- @ApiGroup(group)作用域为@ApiModelProperty(group)，只会校验@ApiModelProperty(group)，存在则为选填。
- 当@Validated(group)和@ApiGroup(group)单独存在时按照以上规则进行注解匹配，如若同时存在，且分组相同时以优先级为准，高优先级会屏蔽低优先级的注解，并且@NotBlank(group)、@NotNull(group)..等注解生效时对于@ApiModelProperty和@ApiModelProperty(group)效果一致；当分组不一致时，也会以高优先级注解首先进行匹配，如果匹配失败才会，以低优先级注解来匹配。

```java

思路图如下：

思路：">":优先级      "-->":决定
  0、vaild
  1、Validate>ApiGroup
  2、Validate-->Notnull （1、匹配成功，必填，且后续操作忽略）
             -->ApiModelProperty （2、验证匹配成功，选填）
     ApiGroup-->ApiModelProperty （3、Validate完全匹配失败，尝试匹配，如果成功：选填）
 
 
 ```
 ```java
 
 example:
 
 1、@Valid ：
    ApiModelProperty (选填)
    Notnull（必填）
 2、@Validate(g1) ：
    ApiModelProperty/ApiModelProperty(g1)/(空)/ApiModelProperty(g2)，Notnull(g1)（必填）
    ApiModelProperty(g1)，Notnull/ Notnull(g2)/(空)(选填)
 3、@ApiGroup(g1) ：
    ApiModelProperty(g1)，NotNull/(空) (选填)
 4、@Validate(g1) ,@ApiGroup(g1):
    ApiModelProperty/(空)/ApiModelProperty(g2)，Notnull(g1)（必填）
    ApiModelProperty(g1)，Notnull/(空)/ Notnull(g2)(选填)
 5、@Validate(g1) ,@ApiGroup(g2):
    ApiModelProperty(g1)（选填）
    ApiModelProperty(g2)（选填）
    Notnull(g1)（必填）
    ApiModelProperty，Notnull(g1)（必填）
    ApiModelProperty(g1)，Notnull（选填）
    ApiModelProperty(g2)，Notnull（选填）
    ApiModelProperty(g1)，Notnull(g1) （必填）
    ApiModelProperty(g2)，Notnull(g2) （选填）
    ApiModelProperty(g1)，Notnull(g2) （选填）
    ApiModelProperty(g2)，Notnull(g1) （必填）

```




