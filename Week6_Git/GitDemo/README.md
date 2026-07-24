# Week 5 – Git Hands-On Labs

## Overview

This folder contains all hands-on lab exercises for Week 5, covering core Git workflows from initial setup through conflict resolution and remote repository management.

---

## Lab Index

| Lab | File | Topic | Duration |
|-----|------|--------|----------|
| Lab 1 | [Lab1_Git_Setup_and_Basic_Commands.md](Lab1_Git_Setup_and_Basic_Commands.md) | Git Setup & Basic Commands | 30 min |
| Lab 2 | [Lab2_Git_Ignore.md](Lab2_Git_Ignore.md) | Git Ignore | 20 min |
| Lab 3 | [Lab3_Branching_and_Merging.md](Lab3_Branching_and_Merging.md) | Branching & Merging | 30 min |
| Lab 4 | [Lab4_Conflict_Resolution.md](Lab4_Conflict_Resolution.md) | Conflict Resolution | 30 min |
| Lab 5 | [Lab5_Cleanup_and_Push_to_Remote.md](Lab5_Cleanup_and_Push_to_Remote.md) | Cleanup & Push to Remote | 10 min |

**Total Estimated Time: ~2 hours**

---

## Sample Files

| File | Description |
|------|-------------|
| `welcome.txt` | Created in Lab 1 — first tracked file |
| `hello.xml` | Created in Lab 4 — used to demonstrate conflict resolution |
| `.gitignore` | Created in Lab 2 — excludes logs and temp files |
| `logs/server.log` | Sample log file — ignored by `.gitignore` |

---

## Lab Prerequisites

- **Git Bash** installed ([https://git-scm.com/downloads](https://git-scm.com/downloads))
- **Notepad++** installed ([https://notepad-plus-plus.org](https://notepad-plus-plus.org))
- **P4Merge** installed for visual diff/merge ([https://www.perforce.com/downloads/visual-merge-tool](https://www.perforce.com/downloads/visual-merge-tool))
- A free **GitLab** account ([https://gitlab.com](https://gitlab.com)) — do **not** use Cognizant credentials

---

## Quick Reference: Git Command Cheat Sheet

### Setup
```bash
git --version                          # Check Git version
git config --global user.name "Name"   # Set username
git config --global user.email "email" # Set email
git config --list                      # View all config
```

### Repository
```bash
git init                    # Initialize new repo
git clone <url>             # Clone remote repo
git remote -v               # View remote connections
```

### Staging & Committing
```bash
git status                  # Check working directory
git add <file>              # Stage a file
git add .                   # Stage all changes
git commit -m "message"     # Commit with message
git commit                  # Commit with editor
```

### Branching
```bash
git branch                  # List branches
git branch <name>           # Create branch
git checkout <branch>       # Switch branch
git checkout -b <branch>    # Create & switch
git merge <branch>          # Merge into current
git branch -d <branch>      # Delete branch
```

### Remote
```bash
git pull origin master      # Pull from remote
git push origin master      # Push to remote
git remote prune origin     # Clean stale branches
```

### History & Diff
```bash
git log --oneline           # Compact log
git log --oneline --graph --decorate --all   # Visual log
git diff <b1>..<b2>         # Diff between branches
git difftool <b1>..<b2>     # Visual diff
```

### Conflict Resolution
```bash
git mergetool               # Open 3-way merge tool
git log --oneline --graph --decorate   # Post-merge log
```
