# Lab 4: Conflict Resolution

## Objectives

- Explain how to resolve conflicts during a merge.

## In this hands-on lab, you will learn how to:

- Implement conflict resolution when multiple users update the master in a way that conflicts with a branch's modification.

## Prerequisites

- Completion of **Lab 3: Branching and Merging** (Git-T03-HOL_001)

> **Notes:** Please follow the below steps for creating a free account in GitHub.
> Do not use cognizant credentials to login to GitHub.

**Estimated time to complete this lab: 30 minutes.**

---

## Step-by-Step Instructions

### 1. Verify master is in a clean state

```bash
git checkout master
git status
```

Expected output:
```
On branch master
nothing to commit, working tree clean
```

### 2. Create a branch "GitWork" and add "hello.xml"

```bash
git checkout -b GitWork
echo "<greeting>Hello from GitWork branch</greeting>" > hello.xml
```

### 3. Update the content of "hello.xml" and observe the status

```bash
echo "<greeting>Updated Hello from GitWork branch</greeting>" > hello.xml
git status
```

Output will show `hello.xml` as modified.

### 4. Commit the changes to the branch

```bash
git add hello.xml
git commit -m "Add hello.xml in GitWork branch"
```

### 5. Switch back to master

```bash
git checkout master
```

### 6. Add "hello.xml" to master with DIFFERENT content

```bash
echo "<greeting>Hello from master branch - different content</greeting>" > hello.xml
```

> **Important:** This content is intentionally different from what was added in the `GitWork` branch to create a conflict.

### 7. Commit the changes to master

```bash
git add hello.xml
git commit -m "Add hello.xml in master with different content"
```

### 8. Observe the commit log

```bash
git log --oneline --graph --decorate --all
```

Expected output showing diverged branches:
```
* f3a1b2c (HEAD -> master) Add hello.xml in master with different content
| * d4e5f6a (GitWork) Add hello.xml in GitWork branch
|/
* abc1234 Add welcome.txt with initial content
```

### 9. Check the differences with Git diff

```bash
git diff master..GitWork
```

### 10. Visual differences using P4Merge tool

```bash
git difftool master..GitWork
```

P4Merge will display the differences between the two versions of `hello.xml`.

### 11. Attempt to merge the branch into master

```bash
git merge GitWork
```

Git will report a **merge conflict**:
```
Auto-merging hello.xml
CONFLICT (add/add): Merge conflict in hello.xml
Automatic merge failed; fix conflicts then commit the result.
```

### 12. Observe the Git conflict markup in the file

```bash
cat hello.xml
```

Git marks the conflicting sections:
```xml
<<<<<<< HEAD
<greeting>Hello from master branch - different content</greeting>
=======
<greeting>Updated Hello from GitWork branch</greeting>
>>>>>>> GitWork
```

- `<<<<<<< HEAD` — content from the current branch (master)
- `=======` — separator
- `>>>>>>> GitWork` — content from the incoming branch (GitWork)

### 13. Use the 3-way merge tool to resolve the conflict

```bash
git mergetool
```

Configure P4Merge as the merge tool if not already set:

```bash
git config --global merge.tool p4merge
git config --global mergetool.p4merge.path "C:/Program Files/Perforce/p4merge.exe"
```

P4Merge opens with 3 panels:
- **Left:** Current branch (master) version
- **Center:** Base version (common ancestor)
- **Right:** Incoming branch (GitWork) version
- **Bottom:** Resolved result

Choose which changes to keep and save the resolved file.

### 14. Commit the resolved changes to master

```bash
git add hello.xml
git commit -m "Resolve merge conflict between master and GitWork"
```

### 15. Check git status and add backup file to .gitignore

After using `git mergetool`, Git may create a backup file `hello.xml.orig`. Add it to `.gitignore`:

```bash
git status
echo "*.orig" >> .gitignore
git add .gitignore
```

### 16. Commit the .gitignore update

```bash
git commit -m "Add *.orig backup files to .gitignore"
```

### 17. List all available branches

```bash
git branch -a
```

### 18. Delete the GitWork branch (now merged into master)

```bash
git branch -d GitWork
```

### 19. Observe the final commit log

```bash
git log --oneline --graph --decorate
```

Expected output showing the merge commit:
```
*   c7d8e9f (HEAD -> master) Resolve merge conflict between master and GitWork
|\
| * d4e5f6a Add hello.xml in GitWork branch
* | f3a1b2c Add hello.xml in master with different content
|/
* abc1234 Add welcome.txt with initial content
```

---

## Conflict Resolution Strategies

| Strategy | Description |
|----------|-------------|
| Accept current (HEAD) | Keep only the master version |
| Accept incoming | Keep only the branch version |
| Accept both | Keep changes from both sides |
| Manual edit | Manually combine the changes |

---

## Key Commands Summary

| Command | Description |
|---------|-------------|
| `git merge <branch>` | Attempt to merge a branch |
| `git diff <b1>..<b2>` | Show differences between branches |
| `git mergetool` | Open visual 3-way merge tool |
| `git log --oneline --graph --decorate --all` | Full commit history with all branches |
| `git branch -d <branch>` | Delete a merged branch |
