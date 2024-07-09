<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">ГГУ Скарына</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Распределение
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <#if notAllScheduleHasTeacher>
                                <a style="color: crimson" class="dropdown-item" href="/main/schedule">Нагрузка</a>
                            <#else>
                                <a class="dropdown-item" href="/main/schedule">Нагрузка</a>
                            </#if>
                        </li>
                        <li>
                            <#if teachersHasOverwork>
                                <a style="color: crimson" class="dropdown-item" href="/main/schedule_teacher">Нагрузка по преподователям</a>
                            <#else>
                                <a class="dropdown-item" href="/main/schedule_teacher">Нагрузка по преподователям</a>
                            </#if>
                        </li>
                    </ul>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Настройки
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="/settings/academic_degree">Преподовательские должности</a></li>
                        <li><a class="dropdown-item" href="/settings/teacher">Преподователи</a></li>
                        <li><a class="dropdown-item" href="/settings/subject">Дисциплины</a></li>
                        <li><a class="dropdown-item" href="/settings/group">Группы студентов</a></li>
                        <li><a class="dropdown-item" href="/settings/sub_group">Подгруппы студентов</a></li>
                    </ul>
                </li>
            </ul>
        </div>

    </div>
</nav>