async function setupNavbar() {
    try {
        const res = await fetch("/me");

        if (!res.ok) return;

        const user = await res.json();

        // default: hidden already in HTML

        if (user.role === "CONDUCTOR" || user.role === "ADMIN") {
            const scanner = document.getElementById("scannerLink");
            if (scanner) scanner.style.display = "inline-block";
        }

        if (user.role === "ADMIN") {
            const admin = document.getElementById("adminLink");
            if (admin) admin.style.display = "inline-block";
        }

    } catch (e) {
        console.error("Navbar error", e);
    }
}

setupNavbar();
