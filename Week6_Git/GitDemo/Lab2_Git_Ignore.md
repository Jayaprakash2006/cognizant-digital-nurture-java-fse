# Lab 2: Git Ignore

## Objectives

- Explain git ignore
- Explain how to ignore unwanted files using git ignore

## In this hands-on lab, you will learn how to:

- Implement git ignore command to ignore unwanted files and folders

## Prerequisites

- Setting up Git environment
- Integrate notepad++ as a default editor
- A Git repository in the local system and a remote repository in GitLab

> **Notes:** Please follow the below steps for creating a free account in GitHub.
> Do not use cognizant credentials to login to GitHub.

**Estimated time to complete this lab: 20 minutes.**

---

## Lab Task

Create a **".log"** file and a **log folder** in the working directory of Git. Update the **.gitignore** file in such a way that on committing, these files (.log extensions and log folders) are ignored.

Verify if the git status reflects the same about working directory, local repository and git repository.

---

## Step-by-Step Instructions

### 1. Navigate to your GitDemo repository

```bash
cd GitDemo
```

### 2. Create a .log file in the working directory

```bash
echo "This is a log file" > application.log
```

### 3. Create a log folder with a file inside

```bash
mkdir logs
echo "Log entry 1" > logs/server.log
```

### 4. Check git status — these files will show as untracked

```bash
git status
```

Output will show `application.log` and the `logs/` folder as untracked files.

### 5. Create the .gitignore file

```bash
notepad++ .gitignore
```

Or create it from the command line:

```bash
touch .gitignore
```

### 6. Add ignore rules to .gitignore

Open `.gitignore` and add the following lines:

```gitignore
# Ignore all .log files
*.log

# Ignore the logs directory
logs/
```

Save and close the file.

### 7. Check git status again

```bash
git status
```

Now `application.log` and `logs/` folder will **no longer appear** as untracked files. Only `.gitignore` itself will show as a new untracked file.

### 8. Stage and commit the .gitignore file

```bash
git add .gitignore
git commit -m "Add .gitignore to exclude log files and log folder"
```

### 9. Verify the final git status

```bash
git status
```

Expected output:
```
On branch master
nothing to commit, working tree clean
```

### 10. Verify ignored files are not tracked

```bash
git ls-files --others --ignored --exclude-standard
```

This lists all files that Git is ignoring.

---

## .gitignore Pattern Reference

| Pattern | Description |
|---------|-------------|
| `*.log` | Ignore all files with .log extension |
| `logs/` | Ignore the entire logs directory |
| `temp/` | Ignore a folder named temp |
| `*.tmp` | Ignore all .tmp files |
| `!important.log` | Do NOT ignore this specific file (exception) |
| `**/*.log` | Ignore .log files in any subdirectory |
| `/debug.log` | Ignore only debug.log at the root |

---

## Summary

- `.gitignore` is used to prevent certain files/folders from being tracked by Git.
- It should be committed to the repository so all team members share the same ignore rules.
- Common use cases: build artifacts, log files, IDE configuration files, dependency folders (e.g., `node_modules/`).
