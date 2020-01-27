function loadPage() {

    let jobsHTML = '<table>' +
        '<tr>' +
        '<th>Id</th>' +
        '<th>Name</th>' +
        '<th>Description</th>' +
        '<th>Price</th>' +
        '</tr>';

    fetch('/jobs/list', {method: 'get'}
    ).then(response => response.json()
    ).then(fruits => {

        for (let fruit of fruits) {

            jobsHTML += `<tr>` +
                `<td>${jobs.id}</td>` +
                `<td>${jobs.name}</td>` +


                `<td>${jobs.description}</td>` +
                `<td>${jobs.price}</td>` +
                `</tr>`;


        }
        fruitsHTML += '</table>';

        document.getElementById("listDiv").innerHTML = fruitsHTML;

        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editFruit);
        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteFruit);
        }

    });

    document.getElementById("saveButton").addEventListener("click", saveEditFruit);
    document.getElementById("cancelButton").addEventListener("click", cancelEditFruit);
    }




}