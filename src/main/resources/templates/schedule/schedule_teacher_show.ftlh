<#import "blocks/common.ftlh" as c>

<@c.page>
    <div class="container text-center md-5">
        <h2> Нагрузка для преподователя </b> <br></h2>
    </div>
    <hr>
    <br>


<div class="container mt-1">
    <div class="row align-items-center">

            <div class="col-md-2">
            <#if teacher.imageFileName??>
                <img src="/images/teachersImages/${teacher.imageFileName}" height="200px" width="200px">
            <#else >
                <img src="/defaultImages/defoultImage.png" height="200px" width="200px">
            </#if>

            </div>
        <div class="col-md-8">
            <h2>${teacher.fio}</h2>
            <p>
                Доджность: ${teacher.academicDegree.academicDegreeName}<br>
                Рабочая ставка: ${teacher.workingRate}<br>
                Максимальное количество часов работы: ${teacher.maximumWorkingHours}<br>
                Текущее количество часов работы: ${teacher.actualWorkingHours}<br>
                <#if teacher.actualWorkingHours gt teacher.maximumWorkingHours >
                    <span style="color: crimson" class="ms-3">Переработка! </span>
                </#if>
            </p>
        </div>
    </div>
    <div class="row-mb-3">
        <br>
        <div class="col-md-6">


            <p class="d-inline-flex gap-1">
                <a class="btn btn-primary" data-bs-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">Посмотреть список текущих предметов</a>
                <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#multiCollapseExample2" aria-expanded="false" aria-controls="multiCollapseExample2">Посмотреть список рекомендованных предметов</button>
                <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target=".multi-collapse" aria-expanded="false" aria-controls="multiCollapseExample1 multiCollapseExample2">Посмотреть оба списка</button>
            </p>
            <div class="row">
                <div class="col">
                    <div class="collapse multi-collapse" id="multiCollapseExample1">
                        <div class="card card-body">
                            <ul class="list-group">
                                <#list teacher.actualSubject as actualsubject>
                                    <li class="list-group-item"> <a href="/schedule/${actualsubject.scheduleId}">${actualsubject.educationalDiscipline.educationalDisciplineName}</a>   | ${actualsubject.subGroupOfStudents.mainGroup.speciality}  | ${actualsubject.subGroupOfStudents.numberOfStudents} чел. <br>${actualsubject.educationalDiscipline.educationalDisciplineDuration} ч.</li>
                                </#list>
                            </ul>

                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="collapse multi-collapse" id="multiCollapseExample2">
                        <div class="card card-body">
                            <ul class="list-group">
                                <#list teacher.recommendedSubject as recomendedsubject>
                                    <li class="list-group-item">${recomendedsubject.educationalDisciplineName}</li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container text-center mt-5">
        <h2> Добавить предметы в нагрузку </b> <br></h2>
    </div>
    <hr>

    <div class="row">
        <div class="accordion accordion-flush" id="accordionFlushExample">
            <#list educationDisciplines as subject>
                <div class="accordion-item">
                    <h2 class="accordion-header" id="heading-${subject_index}">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapse${subject_index}" aria-expanded="false" aria-controls="flush-collapse${subject_index}">
                            ${subject.educationalDisciplineName}
                        </button>
                    </h2>
                    <div id="flush-collapse${subject_index}" class="accordion-collapse collapse" aria-labelledby="heading-${subject_index}" data-bs-parent="#accordionFlushExample">
                        <div class="accordion-body">
                            Длительность предмета: ${subject.educationalDisciplineDuration}ч. <br>
                            Максимальное количество студентов: ${subject.maxStudents} <br>
                            Рекомендуемый преподователь: ${subject.recommendedTeacher.fio}<br>

                            <button type="button" class="btn btn-primary mt-3" data-bs-toggle="modal" data-bs-target="#exampleModal${subject_index}">
                                Добавить предмет
                            </button>

                            <!-- Modal -->
                            <div class="modal fade" id="exampleModal${subject_index}" tabindex="-1" aria-labelledby="exampleModalLabel${subject_index}" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel${subject_index}">Выберите группу студентов</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <form action="/schedule_teacher/${teacher.teacherId}" method="post">
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                    <label for="groupOfStudents${subject_index}" class="form-label">Для добавления предмета в нагрузку преподователя выберите группу студентов для него</label>
                                                    <select class="form-select" id="groupOfStudents${subject_index}" name="groupOfStudentsId" required>
                                                        <#list groupOfStudents as group>
                                                            <option value="${group.groupId}">${group.speciality}  |  ${group.yearOfEntering} год  |  ${group.numberOfStudents} чел.</option>
                                                        </#list>
                                                    </select>
                                                    <input type="hidden" name="subjectId" value="${subject.educationalDisciplineId}">
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="закрыть" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <button type="создать" class="btn btn-primary">Confirm</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>

        </div>
    </div>


</div>




</@c.page>