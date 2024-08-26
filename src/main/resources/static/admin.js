// document.addEventListener('DOMContentLoaded', () => {
//     // Load current admin info
//     fetch('/api/admin/current')
//         .then(response => response.json())
//         .then(admin => {
//             document.getElementById('admin-info').textContent = `${admin.email} with roles: ${admin.roles.join(' ')}`;
//         })
//         .catch(error => console.error('Error:', error));
//
//     // Load all users and populate the table
//     function loadUsers() {
//         fetch('/api/admin/all-users')
//             .then(response => response.json())
//             .then(users => {
//                 const userList = document.getElementById('user-list');
//                 userList.innerHTML = ''; // Clear the list
//                 users.forEach(user => {
//                     const row = document.createElement('tr');
//                     row.innerHTML = `
//                         <td>${user.id}</td>
//                         <td>${user.name}</td>
//                         <td>${user.age}</td>
//                         <td>${user.email}</td>
//                         <td>${user.roles.map(role => `<span class="badge badge-secondary">${role}</span>`).join(' ')}</td>
//                         <td><button class="btn btn-info btn-sm" onclick="editUser(${user.id})">Edit</button></td>
//                         <td><button class="btn btn-danger btn-sm" onclick="deleteUser(${user.id})">Delete</button></td>
//                     `;
//                     userList.appendChild(row);
//                 });
//             })
//             .catch(error => console.error('Error:', error));
//     }
//
//     // Call the function to load users when the page loads
//     loadUsers();
//
//     // Add event listener to the form
//     document.getElementById('user-form').addEventListener('submit', function (event) {
//         event.preventDefault();
//         const formData = new FormData(this);
//         const userData = {
//             name: formData.get('name'),
//             age: formData.get('age'),
//             email: formData.get('email'),
//             password: formData.get('password'),
//             roles: Array.from(formData.getAll('roles'))
//         };
//
//         // Send the request to save the user
//         fetch('/api/admin/save-user?rolesNames=' + userData.roles.join(','), {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify(userData)
//         })
//             .then(response => {
//                 if (response.ok) {
//                     alert('User added successfully!');
//                     loadUsers(); // Reload the users list
//                 } else {
//                     alert('Failed to add user');
//                 }
//             })
//             .catch(error => console.error('Error:', error));
//     });
// });
//
// function editUser(userId) {
//     // Fetch user data and populate form for editing
//     fetch(`/api/admin/update-info/${userId}`)
//         .then(response => response.json())
//         .then(data => {
//             const user = data.user;
//             document.getElementById('name').value = user.name;
//             document.getElementById('age').value = user.age;
//             document.getElementById('email').value = user.email;
//             document.getElementById('roles').value = user.roles.map(role => role.role);
//         })
//         .catch(error => console.error('Error:', error));
// }
//
// function deleteUser(userId) {
//     if (confirm('Are you sure you want to delete this user?')) {
//         fetch(`/api/admin/delete-user/${userId}`, {
//             method: 'DELETE'
//         })
//             .then(response => {
//                 if (response.ok) {
//                     alert('User deleted successfully!');
//                     loadUsers(); // Reload the users list
//                 } else {
//                     alert('Failed to delete user');
//                 }
//             })
//             .catch(error => console.error('Error:', error));
//     }
// }
