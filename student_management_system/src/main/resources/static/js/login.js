//function handle login (multiple languages)
function handleLogin(e, messages) {
  e.preventDefault();

  const usernameInput = document.getElementById("username").value;
  const passwordInput = document.getElementById("password").value;
  const btnLogin = document.getElementById("btnLogin");
  const alertBox = document.getElementById("alertBox");

  // loading effect
  btnLogin.innerHTML = `<i class="fa-solid fa-spinner fa-spin"></i> ${messages.loading}`;
  btnLogin.disabled = true;
  alertBox.classList.add("d-none");

  // call api
  fetch("/api/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username: usernameInput, password: passwordInput }),
  })
    .then((response) =>
      response.json().then((data) => ({ status: response.status, body: data }))
    )
    .then((res) => {
      if (res.status === 200) {
        // success
        localStorage.setItem("accessToken", res.body.accessToken);
        localStorage.setItem("tokenType", res.body.tokenType);

        alertBox.className = "alert alert-success";
        alertBox.textContent = messages.success;
        alertBox.classList.remove("d-none");

        setTimeout(() => {
          const role = res.body.role;

          if (role === "ROLE_ADMIN") {
            window.location.href = "/admin/dashboard";
          } else if (role === "ROLE_TEACHER") {
            window.location.href = "/teacher/dashboard"; // do it later
          } else if (role === "ROLE_STUDENT") {
            window.location.href = "/student/dashboard"; // do it later
          } else {
            window.location.href = "/"; //go to home
          }
        }, 1000);
      } else {
        // failed
        throw new Error(res.body.message || "Login failed");
      }
    })
    .catch((error) => {
      alertBox.className = "alert alert-danger";
      alertBox.textContent = messages.failed;
      alertBox.classList.remove("d-none");

      btnLogin.innerHTML = messages.btnText;
      btnLogin.disabled = false;
    });
}
