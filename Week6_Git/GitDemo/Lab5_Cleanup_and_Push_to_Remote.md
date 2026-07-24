# Lab 5: Cleanup and Push to Remote Git

## Objectives

- Explain how to clean up and push back to remote Git.

## In this hands-on lab, you will learn how to:

- Execute steps involving cleanup and push back to remote Git.

## Prerequisites

- Completion of **Lab 4: Conflict Resolution** (Git-T03-HOL_002)

> **Notes:** Please follow the below steps for creating a free account in GitHub.
> Do not use cognizant credentials to login to GitHub.

**Estimated time to complete this lab: 10 minutes.**

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

If there are uncommitted changes, either commit or stash them before proceeding.

### 2. List all available branches

```bash
git branch -a
```

This shows all local and remote-tracking branches. After completing Lab 4, you should only see `master` and `remotes/origin/master`.

Example output:
```
* master
  remotes/origin/master
```

### 3. Pull the remote Git repository to master

Fetch and merge the latest changes from the remote repository:

```bash
git pull origin master
```

This ensures your local master is in sync with the remote before pushing.

Expected output (if already up to date):
```
From https://gitlab.com/<your-username>/GitDemo
 * branch            master     -> FETCH_HEAD
Already up to date.
```

### 4. Push the pending changes from Lab 4 to the remote repository

```bash
git push origin master
```

Expected output:
```
Counting objects: 12, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (8/8), done.
Writing objects: 100% (12/12), 1.23 KiB | 1.23 MiB/s, done.
Total 12 (delta 2), reused 0 (delta 0)
To https://gitlab.com/<your-username>/GitDemo.git
   abc1234..f3a1b2c  master -> master
```

### 5. Verify changes are reflected in the remote repository

Open your GitLab project in a web browser:

```
https://gitlab.com/<your-username>/GitDemo
```

Verify:
- All commits from Labs 1–4 are visible in the commit history
- The `hello.xml` file is present with the resolved content
- The `.gitignore` file is present
- The `welcome.txt` file is present
- No `GitWork` or `GitNewBranch` branches exist remotely

You can also verify via command line:

```bash
git log --oneline --graph --decorate
```

---

## Common Cleanup Commands

### Remove stale remote-tracking branches

```bash
git remote prune origin
```

### View remote repository details

```bash
git remote -v
```

### Check difference between local and remote

```bash
git log origin/master..master --oneline
```

This shows commits that exist locally but haven't been pushed yet.

---

## Summary of All Labs

| Lab | Topic | Key Commands |
|-----|-------|-------------|
| Lab 1 | Git Setup & Basic Commands | `git init`, `git add`, `git commit`, `git push`, `git pull` |
| Lab 2 | Git Ignore | `.gitignore`, `git ls-files --ignored` |
| Lab 3 | Branching & Merging | `git branch`, `git checkout`, `git merge`, `git difftool` |
| Lab 4 | Conflict Resolution | `git mergetool`, 3-way merge, conflict markup |
| Lab 5 | Cleanup & Push to Remote | `git pull`, `git push`, `git remote prune` |
