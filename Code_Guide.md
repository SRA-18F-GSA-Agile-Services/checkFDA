Code Guide
==========

Commit Messages
---------------

-	Clean, concise, descriptive messages
-	Multi-line messages are encouraged
-	Capitalized messages with the correct punctuation
-	Reference GitHub issues in the commit messages, even if it does not close the issue
-	Check spelling before committing

### Example

```
Added search box to the home page. Ref #22.
Fixed UI issues with the home page. Closes #23.
```

Commits
-------

-	Commits should be made for each atomic change, do not group multiple additions into a single commit
-	Push changes as soon as possible, even if it is to separate branches

Branching
---------

-	Create **Feature Branches** for new features and use **Pull Requests** when merging the features in
-	Commit bug fixes directly into **dev** unless they are extended changes
-	Create personal branches when experimenting or when backing up local changes
-	There is a **master** branch for stable code and a **dev** branch for development
-	Do **not** directly commit to the **master** branch, only merge **dev** into it during release milestones

Code Reviews
------------

-	Assign at least one person to **Pull Requests** for review
-	Regularly check other commits to stay up to date on what others are working on and as an informal code review

Pair Programming
----------------

-	Engage in **Pair Programming**, especially when working on things you are not an expert on
