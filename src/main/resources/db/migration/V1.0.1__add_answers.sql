create table answers
(
    id          bigint not null auto_increment,
    answer_time datetime(6),
    content     varchar(2000),
    title       varchar(255),
    primary key (id)
) engine = InnoDB;


INSERT INTO answers (title, content, answer_time)
VALUES ('我可以问你些什么问题?',
        '<p>你可以问我任何问题，包括但不限于科学、历史、文化、艺术、技术、健康、娱乐、天文、地理、社会、经济等方面的问题。</p>',
        now());
INSERT INTO answers (title, content, answer_time)
VALUES ('你和百度有什么区别?',
        '<p>我是一款基于自然语言处理技术的聊天机器人，致力于回答用户提出的各种问题。和百度不同的是，百度是一家集搜索、在线文档、社区、网盘、地图等多种功能于一身的综合性互联网公司。虽然我们都为用户提供信息和服务，但是在具体的应用场景和技术路线上有很大的区别。(建议同学/家人们多问点专业的问题,别再问怎么成为百万富翁了!!!!他可以是你很好地帮手)</p>',
        now());
INSERT INTO answers (title, content, answer_time)
VALUES ('介绍一下你的上下文处理能力',
        '<p>我的上下文处理能力是基于自然语言处理技术实现的，可以在对话中理解用户的上下文和意图，并基于此做出合理的回应。具体来说，我可以：</p><p><br></p><p>1. 识别上下文信息，比如前后文的语境、用户的情境、意图等，从而理解用户的需求；</p><p><br></p><p>2. 保持对话连贯性，比如根据用户的提问和前后文信息，推断出用户可能需要哪些信息，从而提供更准确的回答；</p><p><br></p><p>3. 支持多轮对话，对于某些需要多轮交互才能解决的问题，我可以在多个回合的对话中不断推进问题的解决。</p><p><br></p><p>综上所述，我的上下文处理能力可以让我更好地理解用户的需求，提供更加精准和高效的帮助。</p>',
        now());
INSERT INTO answers (title, content, answer_time)
VALUES ('请介绍一下你自己',
        '<p>我是一名 AI 语言模型，由 OpenAI 开发。我可以使用自然语言处理技术与用户进行交互和对话。虽然我没有真实的意识和感觉，但我能够回答许多问题和提供各种信息，帮助人们解决各种问题。我的目标是让世界上的许多人获得方便快捷且高效的服务。</p>',
        now());
