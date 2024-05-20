<#import "../blocks/common.ftlh" as c>

<@c.page>

    <div class="container text-center md-5">
        <h2>Переподовательский состав</h2>
    </div>

    <form action="/schedule_teacher" method="get" class="row row-cols-lg-auto g-3  align-items-center">
        <div class="col-12">
            <input type="text" name="query" class="form-control " placeholder="Поиск по названию">
        </div>
        <div class="col-12">
            <button type="submit" class="btn btn-outline-primary ml-5">Найти</button>
        </div>
    </form>

    <div class="row row-cols-4 g-4 mt-3">
        <#list teachers as teacher>

            <div class="col ">
                <div class="card ">
                    <#if teacher.imageFileName??>
                        <img alt="Card image cap" class="card-img-top img-fluid" src="images/teachersImages/${teacher.imageFileName}" style="width: 100%; height: 17rem;" />
                    <#else>
                        <img alt="" class="card-img-top img-fluid" src="/defaultImages/defoultImage.png" style="width: 100%; height: 17rem;" />

                    </#if>
                    <div class="card-body">
                        <h5 class="card-title">${teacher.fio}</h5>
                        <p class="card-text">
                            Должность: ${teacher.academicDegree.academicDegreeName} <br>
                            Ставка: ${teacher.workingRate} <br>
                            Занятость: ${teacher.actualWorkingHours} из ${teacher.maximumWorkingHours}

                        </p>
                        <a href="/schedule_teacher/${teacher.teacherId}" class="btn btn-primary">Подробнее</a>
                        <#if teacher.actualWorkingHours gt teacher.maximumWorkingHours >
                            <span style="color: crimson" class="ms-3">Переработка! </span>

                        </#if>

                    </div>
                </div>
            </div>

        <#else>
            <h3>Преподователи не найдены</h3>
        </#list>
    </div>

</@c.page>