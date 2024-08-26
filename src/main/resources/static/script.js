document.addEventListener("DOMContentLoaded", function() {
    fetch("/api/admin/all-users")
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector("#userTable tbody");

            data.forEach(user => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(role => role.role).join(", ")}</td>
                `;

                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error("Error fetching user data:", error));
});
