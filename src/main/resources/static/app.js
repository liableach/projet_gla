async function getCurrentUser() {

    try {

        const response = await fetch("/me");

        if (!response.ok) {
            return null;
        }

        return await response.json();

    } catch (err) {

        console.error(err);
        return null;
    }
}

async function setupNavbar() {

    const scannerLink =
        document.getElementById("scannerLink");

    const adminLink =
        document.getElementById("adminLink");

    const loginLink =
        document.getElementById("loginLink");

    const logoutBtn =
        document.getElementById("logoutBtn");

    const user = await getCurrentUser();

    if (!user) {

        if (logoutBtn) {
            logoutBtn.style.display = "none";
        }

        return;
    }

    if (loginLink) {
        loginLink.style.display = "none";
    }

    if (logoutBtn) {
        logoutBtn.style.display = "inline-block";
    }

    if (
        user.role === "CONDUCTOR" ||
        user.role === "ADMIN"
    ) {

        if (scannerLink) {
            scannerLink.style.display = "inline-block";
        }
    }

    if (user.role === "ADMIN") {

        if (adminLink) {
            adminLink.style.display = "inline-block";
        }
    }
}

async function logout() {

    try {

        const response = await fetch("/auth/logout", {
            method: "POST"
        });

        if (!response.ok) {
            alert("Logout failed");
            return;
        }

        window.location.href = "login.html";

    } catch (err) {

        console.error(err);
        alert("Error during logout");
    }
}

async function requireAuth() {

    const user = await getCurrentUser();

    if (!user) {

        alert("Please login first");

        window.location.href = "login.html";

        return null;
    }

    return user;
}

async function requireAdmin() {

    const user = await requireAuth();

    if (!user) return null;

    if (user.role !== "ADMIN") {

        alert("Access denied");

        window.location.href = "index.html";

        return null;
    }

    return user;
}

async function requireConductor() {

    const user = await requireAuth();

    if (!user) return null;

    if (
        user.role !== "CONDUCTOR" &&
        user.role !== "ADMIN"
    ) {

        alert("Access denied");

        window.location.href = "index.html";

        return null;
    }

    return user;
}

setupNavbar();
