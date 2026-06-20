<#import "layout.ftl" as l>
<@l.base_layout>
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Active Sessions</h5>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead class="table-light">
                    <tr>
                        <th>User ID</th>
                        <th>Session ID</th>
                        <th>Location</th>
                        <th>Access Type</th>
                        <th>Created</th>
                        <th>Last Accessed</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list sessions as session>
                        <tr>
                            <td>${session.userId?string}</td>
                            <td><code>${session.sessionId?string}</code></td>
                            <td>${session.location?string}</td>
                            <td><span class="badge bg-info text-dark">${session.accessType?string}</span></td>
                            <td>${session.creationTime?string}</td>
                            <td>${session.lastAccessedTime?string}</td>
                        </tr>
                    <#else>
                        <tr>
                            <td colspan="6" class="text-center">No active sessions found.</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</@l.base_layout>