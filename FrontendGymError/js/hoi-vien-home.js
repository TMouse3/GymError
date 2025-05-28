// scripts for hoi-vien-home.html 

document.addEventListener('DOMContentLoaded', function() {
    // Select list items with data-section attribute
    const menuItems = document.querySelectorAll('.sidebar ul.menu li[data-section]');
    const contentBlocks = document.querySelectorAll('.main-content .sample-data-block');
    const welcomeBlock = document.querySelector('.main-content .welcome-block');

    // Function to show a specific content block and hide others
    function showContentBlock(sectionId) {
        // Hide welcome block
        if (welcomeBlock) {
            welcomeBlock.style.display = 'none';
        }

        contentBlocks.forEach(block => {
            if (block.id === sectionId) {
                block.style.display = 'block';
            } else {
                block.style.display = 'none';
            }
        });
    }

    // Function to set active menu item
    function setActiveMenuItem(clickedItem) {
        menuItems.forEach(item => {
            item.classList.remove('active');
        });
         if (clickedItem) {
          clickedItem.classList.add('active');
        }
    }

    // Add click event listeners to menu items
    menuItems.forEach(item => {
        item.addEventListener('click', function(event) {
            event.preventDefault(); // Prevent default behavior (though li doesn't have default link behavior)
            const sectionId = this.getAttribute('data-section');
            showContentBlock(sectionId);
            setActiveMenuItem(this);
        });
    });

    // Show the initial content block based on the hash in the URL or default to the welcome block
    const initialSectionId = window.location.hash ? window.location.hash.substring(1) : null; // Get section id from hash
    if (initialSectionId) {
        showContentBlock(initialSectionId);
        const initialActiveItem = document.querySelector('.sidebar ul.menu li[data-section="' + initialSectionId + '"]');
        setActiveMenuItem(initialActiveItem);
    } else {
        // Show welcome block and set no menu item as active initially
        if (welcomeBlock) {
            welcomeBlock.style.display = 'block';
        }
        contentBlocks.forEach(block => { // Ensure all sample data blocks are hidden
            block.style.display = 'none';
        });
        setActiveMenuItem(null); // No active menu item initially
    }
}); 