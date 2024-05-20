<#import "../blocks/commonNoAnimation.ftlh" as c>

<@c.page>
    <div class="container text-center md-5">
        <h2>Переподовательские должности</h2>
    </div>

    <div class="m-4">
        <div class="row row-cols-4 g-4 mt-3">
            <#list academicDegrees as academicDegree>
                <div class="col">
                    <div class="card" style="width: 18rem;">
                        <div class="card-body">
                            <h5 class="card-title">${academicDegree.academicDegreeName}</h5>
                            <p class="card-text">
                                Кол-во рабочих часов: ${academicDegree.workLimit}
                            </p>
                            <form method="get" action="/academic_degree/${academicDegree.academicDegreeId}">
                                <button type="submit" class="btn btn-outline-primary">Подробнее</button>
                            </form>

                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
<hr>
<br>

    <p>
        <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseWidthExample" aria-expanded="false" aria-controls="collapseWidthExample">
            Toggle width collapse
        </button>
    </p>
    <div style="min-height: 120px;">
        <div class="collapse collapse-horizontal" id="collapseWidthExample">
            <div class="card card-body" style="width: 300px;">
                <form action="/academic_degree" method="post" class="row g-3 needs-validation" novalidate>
                    <div class="col-md-4">
                        <label for="validationCustom01" class="form-label">Название Должности</label>
                        <input type="text" name="academicDegreeName" class="form-control" id="validationCustom01" required>
                        <div class="valid-feedback">
                            Looks good!
                        </div>
                    </div>
                    <div class="col-md-4">
                        <label for="validationCustom02" class="form-label">Количество рабочих часов</label>
                        <input type="text" name="workLimit" class="form-control" id="validationCustom02" required>
                        <div class="valid-feedback">
                            Looks good!
                        </div>
                    </div>
                    <div class="col-12">
                        <button class="btn btn-primary" type="submit">Submit form</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</@c.page>