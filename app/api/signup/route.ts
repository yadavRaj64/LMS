import { NextRequest, NextResponse } from "next/server";
import user from "@/app/api/user.json";
import fs from "fs";

export async function POST(req: NextRequest): Promise<NextResponse> {
  const data = await req.json();
  const temp = user.find((item) => item.email == data.email);
  if (!temp) {
    user.push(data);
    fs.writeFileSync("user.json", JSON.stringify(user));
    return NextResponse.json({ message: "User added successfully" });
  } else {
    return NextResponse.json(
      { message: "User already exist" },
      { status: 400 }
    );
  }
}
