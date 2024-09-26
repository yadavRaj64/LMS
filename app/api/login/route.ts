import { NextRequest, NextResponse } from "next/server";
import user from "@/app/api/user.json";

export async function POST(req: NextRequest): Promise<NextResponse> {
  const data = await req.json();
  const temp = user.find((item) => item.email == data.email);
  if (temp) {
    if (temp.password == data.password) {
      return NextResponse.json({ message: "User Logged In" }, { status: 200 });
    } else {
      return NextResponse.json(
        { message: "Incorrect Password" },
        { status: 400 }
      );
    }
  } else {
    return NextResponse.json(
      { message: "User Does not Exist" },
      { status: 400 }
    );
  }
}
