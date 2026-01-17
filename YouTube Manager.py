import json
import tkinter as tk
from tkinter import messagebox, simpledialog
from tkinter import ttk


def load_data():
    try:
        with open('youtube.txt', 'r') as file:
            return json.load(file)
    except FileNotFoundError:
        return []
    except json.JSONDecodeError:
        messagebox.showerror("Error", "Error reading the video data. The file might be corrupted.")
        return []


def save_data_helper(videos):
    try:
        with open('youtube.txt', 'w') as file:
            json.dump(videos, file, indent=4)
    except IOError:
        messagebox.showerror("Error", "An error occurred while saving the video data.")


def refresh_table(tree):
    for row in tree.get_children():
        tree.delete(row)
    videos = load_data()
    for index, video in enumerate(videos, start=1):
        tree.insert("", "end", values=(index, video['name'], video['time']))


def add_video(tree):
    name = simpledialog.askstring("Input", "Enter video name:")
    time = simpledialog.askstring("Input", "Enter video time (e.g., 10:00):")

    if name and time:
        videos = load_data()
        videos.append({'name': name, 'time': time})
        save_data_helper(videos)
        messagebox.showinfo("Success", "Video added successfully.")
        refresh_table(tree)
    else:
        messagebox.showwarning("Input Error", "Video name and time cannot be empty.")


def update_video(tree):
    selected_item = tree.focus()
    if not selected_item:
        messagebox.showwarning("Selection Error", "Please select a video to update.")
        return

    index = int(tree.item(selected_item, "values")[0]) - 1
    videos = load_data()

    name = simpledialog.askstring("Input", "Enter the new video name:", initialvalue=videos[index]['name'])
    time = simpledialog.askstring("Input", "Enter the new video time:", initialvalue=videos[index]['time'])

    if name and time:
        videos[index] = {'name': name, 'time': time}
        save_data_helper(videos)
        messagebox.showinfo("Success", "Video updated successfully.")
        refresh_table(tree)
    else:
        messagebox.showwarning("Input Error", "Video name and time cannot be empty.")


def delete_video(tree):
    selected_item = tree.focus()
    if not selected_item:
        messagebox.showwarning("Selection Error", "Please select a video to delete.")
        return

    index = int(tree.item(selected_item, "values")[0]) - 1
    videos = load_data()

    confirm = messagebox.askyesno("Confirm Deletion", f"Are you sure you want to delete video: {videos[index]['name']}?")
    if confirm:
        del videos[index]
        save_data_helper(videos)
        messagebox.showinfo("Success", "Video deleted successfully.")
        refresh_table(tree)


def exit_app():
    confirm = messagebox.askyesno("Exit", "Are you sure you want to exit?")
    if confirm:
        root.quit()


def create_main_window():
    global root
    root = tk.Tk()
    root.title("YouTube Manager")
    root.minsize(600, 400)

    # Apply a theme
    style = ttk.Style()
    style.theme_use("clam")
    style.configure("Treeview", font=("Arial", 12), rowheight=25)
    style.configure("Treeview.Heading", font=("Arial", 14, "bold"))
    style.configure("TButton", font=("Arial", 12), padding=10)
    style.configure("TLabel", font=("Arial", 12))

    root.configure(bg="#f5f5f5")

    # Create Treeview
    tree = ttk.Treeview(root, columns=("ID", "Name", "Duration"), show="headings")
    tree.heading("ID", text="ID")
    tree.heading("Name", text="Name")
    tree.heading("Duration", text="Duration")
    tree.column("ID", width=50, anchor="center")
    tree.column("Name", width=300, anchor="w")
    tree.column("Duration", width=100, anchor="center")
    tree.pack(expand=True, fill="both", pady=10, padx=10)

    # Populate initial data
    refresh_table(tree)

    # Buttons
    button_frame = tk.Frame(root, bg="#f5f5f5")
    button_frame.pack(pady=10)

    ttk.Button(button_frame, text="Add Video", command=lambda: add_video(tree)).grid(row=0, column=0, padx=5)
    ttk.Button(button_frame, text="Update Video", command=lambda: update_video(tree)).grid(row=0, column=1, padx=5)
    ttk.Button(button_frame, text="Delete Video", command=lambda: delete_video(tree)).grid(row=0, column=2, padx=5)
    ttk.Button(button_frame, text="Exit", command=exit_app).grid(row=0, column=3, padx=5)

    root.mainloop()


def create_login_window():
    login_window = tk.Tk()
    login_window.title("Login")
    login_window.configure(bg="#f5f5f5")

    style = ttk.Style()
    style.configure("TLabel", font=("Arial", 12))
    style.configure("TButton", font=("Arial", 12), padding=10)

    ttk.Label(login_window, text="Username:").pack(pady=5)
    username_entry = ttk.Entry(login_window, font=("Arial", 12))
    username_entry.pack(pady=5)

    ttk.Label(login_window, text="Password:").pack(pady=5)
    password_entry = ttk.Entry(login_window, show="*", font=("Arial", 12))
    password_entry.pack(pady=5)

    def validate_login():
        username = username_entry.get()
        password = password_entry.get()

        if username == "a2k" and password == "7":
            login_window.destroy()
            create_main_window()
        else:
            messagebox.showerror("Login Error", "Invalid username or password.")

    ttk.Button(login_window, text="Login", command=validate_login).pack(pady=10)
    login_window.mainloop()


if __name__ == "__main__":
    create_login_window()
