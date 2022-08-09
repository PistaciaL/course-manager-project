--room
create table room
(
    room_id    smallserial primary key,
    "location" varchar(32) not null,
    seat       smallint    not null check (seat > 0),
    is_deleted boolean     not null default false
);
comment on column room."location" is '教室位置,学院楼+教室号';
comment on column room.seat is '教室的最大人数';


--term
create table term
(
    term_id    smallint primary key,
    "name"     varchar(32) not null unique,
    start_date date        not null,
    end_date   date        not null,
    check (end_date > start_date)
);
comment on table term is '学期信息';

-- college
create table college
(
    college_id smallserial primary key,
    "name"     varchar(32) not null unique
);

-- specialty
create table specialty
(
    specialty_id smallserial primary key,
    "name"       varchar(32) not null unique,
    college_id   smallint    not null references college
);
comment on table specialty is '学院所属的专业';

--guide
create table guide
(
    guide_id     smallserial primary key,
    specialty_id smallint not null references specialty,
    study_year   smallint check (study_year > 1960 and study_year < 2099),
    unique (specialty_id, study_year)
);
comment on table guide is '学业指导计划';

--subject
create table subject
(
    subject_id  smallserial primary key,
    "name"      varchar(32) not null,
    college_id  smallint references college,
    lesson_hour smallint    not null check (lesson_hour > 0),
    credit      smallint    not null check (credit > 0),
    unique ("name", college_id)
);
comment on column subject.college_id is '科目所属学院的id,可以为null';
comment on column subject.lesson_hour is '课时数';
comment on column subject.credit is '学分';

-- work_number
create table work_number
(
    wn_id       varchar(10) primary key,
    "name"      varchar(30) not null,
    id_number   varchar(40) not null,
    create_time timestamp   not null default CURRENT_TIMESTAMP,
    is_deleted  boolean     not null default false
);
create index idx_wn_id_name on work_number using btree(wn_id, "name");

--user
create type identity_enum as enum ('学生','教师');
create type permission_enum as enum ('普通用户', '院级管理员', '校级管理员');
create table "user"
(
    user_id      varchar(16) primary key references work_number,
    "name"       varchar(64)     not null,
    avatar       varchar(64),
    phone        varchar(16) unique,
    "password"   varchar(128)    not null,
    college_id   smallint references college,
    specialty_id smallint references specialty,
    study_year   smallint check (study_year > 1960 and study_year < 2099),
    job_title    varchar(32),
    "identity"   identity_enum   not null default '学生',
    "permission" permission_enum not null default '普通用户',
    create_time  timestamp       not null default CURRENT_TIMESTAMP,
    is_delete    boolean         not null default false
);
create index idx_user_name_passwd on "user" using btree("name", "password");
comment on column "user"."name" is '真实姓名';
comment on column "user".avatar is '头像url';
comment on column "user".college_id is '所属学院id';
comment on column "user".specialty_id is '所属专业id(学生)';
comment on column "user".study_year is '入学年份(学生)';
comment on column "user".job_title is '职位(教师)';

--course
create table course
(
    course_id       serial primary key,
    subject_id      smallint    not null references subject,
    teacher_id      varchar(16) not null references "user",
    term_id         smallint    not null references term,
    max_student     smallint    not null check (max_student > 0),
    exist_student   smallint    not null default 0,
    mark_is_publish boolean              default false
);
comment on column course.term_id is '课程开设的学期的id';
comment on column course.max_student is '课程最大学生容量';
comment on column course.exist_student is '当前已经选课成功的学生数量';

--exam
create table exam
(
    exam_id    serial primary key,
    course_id  int       not null references course,
    start_time timestamp not null check (start_time > CURRENT_TIMESTAMP),
    end_time   timestamp not null check (end_time > start_time)
);

--schedule
create table schedule
(
    schedule_id      serial primary key,
    course_id        int      not null references course,
    room_id          int      not null references room,
    class_date       date     not null,
    start_class_hour smallint not null check (start_class_hour > 0),
    end_class_hour   smallint not null check (end_class_hour < 14)
);
comment on table schedule is '课程的日程表';
comment on column schedule.class_date is '上课的日期';
comment on column schedule.start_class_hour is '上课开始的课时';
comment on column schedule.end_class_hour is '上课结束的课时';

--student_course
create table student_course
(
    sc_id      serial primary key,
    course_id  int         not null references course,
    student_id varchar(16) not null references "user"
);
comment on table student_course is '学生选课信息';

--apply
create type apply_enum as enum ('选课申请','退课申请','成绩申请');
create table apply
(
    apply_id    serial primary key,
    "type"      apply_enum  not null,
    user_from   varchar(16) not null references "user",
    user_to     varchar(16) references "user",
    college_to  smallint references college,
    course_id   int         not null references course,
    student_id  varchar(16) references "user",
    new_mark    smallint    not null check ( new_mark >= 0 and new_mark <= 100 ),
    "content"   text        not null,
    apply_time  timestamp   not null default CURRENT_TIMESTAMP,
    handle_time timestamp,
    handle_user varchar(16) not null references "user",
    is_agree    boolean,
    check ((college_to != null and user_to = null) or (college_to = null and user_to != null))
);
comment on column apply.user_to is '接收该消息的用户';
comment on column apply.college_to is '接受该消息的学院';
comment on column apply.course_id is '申请的消息对应的课程';
comment on column apply.student_id is '需要修改成绩的学生id';
comment on column apply.new_mark is '修改后的新成绩';
comment on column apply.is_agree is '申请是否通过,申请未处理时为null';

--notice
create type notice_enum as enum ('选课申请','退课申请','补选结果','退课结果','发布考试','发布成绩','修改成绩','成绩申请');
create table notice
(
    notice_id       serial primary key,
    "type"          notice_enum not null,
    "content"       text        not null,
    receive_user_id varchar(16) not null references "user",
    is_read         boolean     not null default false,
    create_time     timestamp   not null default CURRENT_TIMESTAMP
);
--course_choice
create type cc_type_enum as enum ('筹码选课','先选先得');
create table course_choice
(
    cc_id        serial primary key,
    term_id      smallint     not null references term,
    start_time   timestamp    not null check ( start_time > CURRENT_TIMESTAMP ),
    end_time     timestamp    not null check ( end_time > start_time ),
    "type"       cc_type_enum not null,
    default_chip smallint check ( default_chip > 0 )
);
comment on table course_choice is '选课开启时间段的信息';
comment on column course_choice.default_chip is '筹码选课阶段,每个人初始的筹码数';

--student_course_chip
create table student_course_chip
(
    scc_id      serial primary key,
    course_id   int         not null references course,
    student_id  varchar(16) not null references "user",
    chip        smallint    not null default 0 check ( chip >= 0 ),
    choice_time timestamp   not null default current_timestamp,
    cc_id       int         not null references course_choice
);
comment on table student_course_chip is '筹码选课阶段学生的选课信息缓存';
comment on column student_course_chip.chip is '选课所给的筹码数';
comment on column student_course_chip.cc_id is '选课轮次对应的id';

--exam_student
create table exam_student
(
    es_id      serial primary key,
    exam_id    int         not null references exam,
    room_id    smallint    not null references room,
    student_id varchar(16) not null references "user"
);
comment on table exam_student is '考试的考场信息';

--mark_model
create table mark_model
(
    mm_id      serial primary key,
    course_id  int           not null references course,
    "name"     varchar(16)   not null,
    percentage decimal(3, 2) not null check (percentage > 0 and percentage < 1)
);
comment on table mark_model is '课程分数模块及占比信息';
comment on column mark_model.percentage is '该模块占总成绩的占比(小数)';

--mark_info
create table mark_info
(
    mark_id    serial primary key,
    student_id varchar(16) not null references "user",
    mm_id      int         not null references mark_model,
    mark       smallint    not null check ( mark >= 0 and mark <= 100 )
);
comment on table mark_info is '课程分数信息';
comment on column mark_info.mm_id is '对应模块的id';
comment on column mark_info.mark is '对应模块的成绩(百分制)';

--exam_room
create table exam_room
(
    er_id serial primary key,
    room  smallint references room
);
comment on table exam_room is '考试的教室信息';

-- guide_subject
create table guide_subject
(
    gs_id      serial primary key,
    guide      smallint references guide,
    subject_id smallint references subject
);
comment on table guide_subject is '学业指导计划的课程信息';

--course_log
create table course_log(
                           clog_id serial primary key,
                           operation_user_id varchar(16) not null references "user",
                           operation_time timestamp not null default CURRENT_TIMESTAMP,
                           operated_course_id int not null references course,
                           changed_data json not null
);

--user_log
create table user_log(
                         ulog_id serial primary key,
                         operation_user_id varchar(16) not null references "user",
                         operation_time timestamp not null default CURRENT_TIMESTAMP,
                         operated_user_id varchar(16) not null references "user",
                         changed_data json not null
);