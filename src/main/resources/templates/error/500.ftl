<#import "../layout.ftl" as l>
<@l.base_layout>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8 text-center">
                <h1 class="display-1 text-danger">500</h1>
                <h2 class="mb-4">Internal Server Error</h2>
                <p class="lead">Something went wrong on our end. Our team has been notified.</p>

                <#if errorMessage??>
                    <div class="alert alert-warning mt-4 text-start">
                        <strong>Technical Details:</strong>
                        <pre>${errorMessage}</pre>
                    </div>
                </#if>

                <div class="mt-4">
                    <a href="/" class="btn btn-primary">Return Home</a>
                    <a href="/game" class="btn btn-outline-secondary">Go to Game</a>
                </div>
            </div>
        </div>
    </div>
</@l.base_layout>