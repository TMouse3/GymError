// scripts for pt-home.html

document.addEventListener('DOMContentLoaded', function() {
    const menuItems = document.querySelectorAll('.sidebar ul.menu li[data-section]');
    const contentBlocks = document.querySelectorAll('.main-content .sample-data-block');
    const welcomeBlock = document.querySelector('.main-content .welcome-block');

    // Hide all content blocks initially
    contentBlocks.forEach(block => {
        block.style.display = 'none';
    });
    // Also hide welcome block initially, will show later if needed
     if (welcomeBlock) {
         welcomeBlock.style.display = 'none';
     }

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
            event.preventDefault(); // Prevent default behavior
            const sectionId = this.getAttribute('data-section');
            showContentBlock(sectionId);
            setActiveMenuItem(this);
        });
    });

    // Determine and show the initial content
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
        setActiveMenuItem(null); // No active menu item initially
    }
}); 