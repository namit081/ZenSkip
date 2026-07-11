from PIL import Image
import os
import sys

# Get image path from arg
img_path = sys.argv[1]
res_path = "app/src/main/res/"

sizes = {
    "mdpi": 48,
    "hdpi": 72,
    "xhdpi": 96,
    "xxhdpi": 144,
    "xxxhdpi": 192
}

img = Image.open(img_path)

for dpi, size in sizes.items():
    folder = os.path.join(res_path, f"mipmap-{dpi}")
    os.makedirs(folder, exist_ok=True)
    
    resized = img.resize((size, size), Image.Resampling.LANCZOS)
    
    # Save as ic_launcher and round variant
    resized.save(os.path.join(folder, "ic_launcher.png"))
    resized.save(os.path.join(folder, "ic_launcher_round.png"))

print("Icons generated!")
