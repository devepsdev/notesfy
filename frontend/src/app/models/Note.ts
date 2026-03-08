export default interface Note {
  id?: number;
  uuid?: string;
  title: string;
  content?: string;
  marked: boolean;
  categoryId?: number;
  categoryName?: string;
  categoryColor?: string;
  createdAt?: string;
  updatedAt?: string;
}
